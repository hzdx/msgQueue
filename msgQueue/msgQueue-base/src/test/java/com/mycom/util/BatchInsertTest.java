package com.mycom.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycom.domain.Subject;
import com.mycom.test.thread.BatchInsertHandler;

public class BatchInsertTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring.xml");
		BatchInsertHandler bh = context.getBean(BatchInsertHandler.class);
		System.out.println("*******************************************");
		long beginTime = System.currentTimeMillis();
		List<Subject> list = new ArrayList<Subject>();
		for(int i=0;i<300000;i++){
			Subject sub = new Subject();
			sub.setId(i+1);
			sub.setMsg("abc"+i);
			list.add(sub);
		}
		long middleTime = System.currentTimeMillis();
		System.out.println("*****************************************************");
		System.out.println("数据初始化完成，耗时 ："+(middleTime-beginTime)+" ms");
		bh.mutiThreadInsert(list,8); // 102641ms
		//bh.insertOnce(list);  //83287
		long endTime = System.currentTimeMillis();
		System.out.println("*****************************************************");
		System.out.println("插入完成 ，耗时 ：" +(endTime-middleTime)+ " ms");
		context.close();
	}

}
