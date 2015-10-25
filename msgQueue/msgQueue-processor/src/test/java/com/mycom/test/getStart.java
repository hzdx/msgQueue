package com.mycom.test;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
/**
 * 
 * getStart
 * 连接hazelcast测试
 */
public class getStart {

  public static void main(String[] args) {
    
    //defalut("ytoConfigMap");//默认配置无数据。
    spring();      //能通过指定配置连接到hazelcast
  }

  private static void defalut(String config){
    Config cfg = new Config();
    HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
    Map<String, String> configMap = instance.getMap(config);
    listMap(configMap);
  }
  
  private static void spring(){
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    HazelcastInstance instance = context.getBean(HazelcastInstance.class);
    Queue queue = instance.getQueue("subjectQueue");
    for(Object obj:queue){
    	System.out.println(obj);
    }
   // listMap(configMap);
  }
  
  private static void listMap(Map map){
    Set<Entry<String, String>> set = map.entrySet();
    for(Map.Entry entry:set){
      System.out.println(entry.getKey()+"..."+entry.getValue());
    }
  }
}
