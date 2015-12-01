package com.mycom.test.thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.sql.DataSource;






import com.mycom.domain.Subject;

public class BatchInsertHandler {
	private int threadCount = 20;
	@Resource
	private DataSource dataSource;
	
	//单线程方式
	public void insertOnce(List<Subject> list){
		try{
			Connection conn = dataSource.getConnection();
			execute(conn,list);
		}catch(Exception e){			
		}
	}
	
	//多线程方式
	public void mutiThreadInsert(List<Subject> list){
		int count = list.size();
		//每个线程需处理记录数
		int part = count % threadCount==0?count / threadCount:(count/threadCount+1);
		//ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<threadCount;i++){
			int startIndex = part * i;
			int endIndex = part * (i + 1);
			if (endIndex > count) {
				endIndex = count;
			}
			final List<Subject> feed = list.subList(startIndex, endIndex);
			final Connection conn = getConnection();
			InsertDbThread insert = new InsertDbThread();
			insert.setConnection(conn);
			insert.setList(list);
			new Thread(new Runnable() {				
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					System.out.println(Thread.currentThread().getName()+" start!");
					execute(conn,feed);
					System.out.println(Thread.currentThread().getName()
							+" end ,cost ms:"+(System.currentTimeMillis()-time));
				}
			}).start();
			//exec.execute(insert);
		}
	}
	
	private void execute(Connection conn,List<Subject> list){
		try {
			long time = System.currentTimeMillis();
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
			System.out.println(Thread.currentThread().getName()+"execute cost :" +(System.currentTimeMillis()-time)+" ms");
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
	
	public void setThreadCount(int threadCount){
		this.threadCount = threadCount;
	}
}
