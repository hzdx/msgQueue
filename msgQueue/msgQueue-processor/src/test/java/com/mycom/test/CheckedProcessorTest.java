package com.mycom.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycom.process.Processor;

public class CheckedProcessorTest {

  @Test
  public void test() {

    ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext("classpath*:spring.xml");
    List<Processor> list = (List<Processor>) context.getBean("processorList");
    // context.getBean(B2EProcessor.class);

    
    System.out.println(list.size());
//    bean.run();

    context.close();
  }

}
