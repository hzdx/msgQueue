package com.mycom.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycom.domain.Subject;

public class JDBCTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring.xml");
		JdbcUtil util = context.getBean(JdbcUtil.class);
		Subject sub = new Subject(3, "ccc");
		util.save(sub);

		context.close();
	}

}
