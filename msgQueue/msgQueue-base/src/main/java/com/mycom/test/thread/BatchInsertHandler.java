package com.mycom.test.thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.mycom.domain.Subject;

public class BatchInsertHandler {
	@Resource
	private DataSource dataSource;
	
	//单线程方式
	public void insertOnce(List<Subject> list){
		try{
			Connection conn = dataSource.getConnection();
			executeUpdate(conn,list);
		}catch(Exception e){			
		}
	}
	
	//多线程方式
	public void mutiThreadInsert(List<Subject> list,int threadCount){
		int count = list.size();
		//每个线程需处理记录数
		int part = count % threadCount==0?count / threadCount:(count/threadCount+1);
		List<Future<String>> resultList = new ArrayList<Future<String>>(); 
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		for(int i=0;i<threadCount;i++){
			int startIndex = part * i;
			int endIndex = part * (i + 1);
			if (endIndex > count) {
				endIndex = count;
			}
			final List<Subject> feed = list.subList(startIndex, endIndex);
			final Connection conn = getConnection();
//			InsertDbThread insert = new InsertDbThread();
//			insert.setConnection(conn);
//			insert.setList(list);
			Callable<String> runner = new Callable<String>() {				
				@Override
				public String call() {
					long time = System.currentTimeMillis();
					System.out.println(Thread.currentThread().getName()+" start!");
					executeUpdate(conn,feed);
					System.out.println(Thread.currentThread().getName()
							+" end ,cost ms:"+(System.currentTimeMillis()-time));
					return Thread.currentThread().getName()+" is complete!";
				}
			};
			Future<String> fs = exec.submit(runner);
			resultList.add(fs);
		}
		 for (Future<String> fs : resultList) { 
             try { 
                  System.out.println(fs.get());     //打印各个线程（任务）执行的结果 
             } catch (Exception e) {
             } finally { 
                     //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。 
            	 exec.shutdown(); 
             } 
		 } 
		
	}
	
	private void executeUpdate(Connection conn,List<Subject> list){
		try {
			conn.setAutoCommit(false);
			String sql = "insert into subject(id,msg) values(?,?)";
			PreparedStatement prepst = conn.prepareStatement(sql);
			for (Subject sub : list) {
				prepst.setObject(1, sub.getId());
				prepst.setObject(2, sub.getMsg());
				prepst.addBatch();
			}		
			prepst.executeBatch();		
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private synchronized Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;	
	}
	
}
