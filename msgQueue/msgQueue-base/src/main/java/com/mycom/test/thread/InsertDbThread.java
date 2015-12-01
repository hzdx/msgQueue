package com.mycom.test.thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.mycom.domain.Subject;

public class InsertDbThread implements Runnable {
	private List<Subject> list = null;
	private Connection conn = null;
	public InsertDbThread(){}
	
	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName()+" started");
			long threadBeginTime = System.currentTimeMillis();
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
			System.out.println(Thread.currentThread().getName()+"execute cost :" +(System.currentTimeMillis()-threadBeginTime)+" ms");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void setList(List<Subject> list) {
		this.list = list;
	}

}
