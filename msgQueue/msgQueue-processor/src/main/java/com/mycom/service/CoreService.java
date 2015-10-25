package com.mycom.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycom.domain.Subject;
import com.mycom.util.JdbcUtil;

public class CoreService {
	@Autowired
	private JdbcUtil jdbcUtil;
	public void processMsg(Object obj){
		Subject sub = (Subject)obj;
		jdbcUtil.save(sub);
		System.out.println(sub+" is saved!");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
