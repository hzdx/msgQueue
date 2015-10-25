package com.mycom.process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycom.domain.Subject;
import com.mycom.service.CoreService;

public class Processor implements Runnable {

    private AtomicBoolean running = new AtomicBoolean(true);

    @Autowired
    private CoreService coreService = null;

    @Resource
	private BlockingQueue<Subject> subjectQueue = null;
    /*public Processor() {
    	System.out.println(Thread.currentThread().getId()+" process is starting!=======");
    }*/

    public void run() {
        while (running.get()) {
            Object obj;
            try {
              obj = subjectQueue.poll(5, TimeUnit.SECONDS);
                if (obj != null) {
                  coreService.processMsg(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void setCoreService(CoreService coreService) {
      this.coreService = coreService;
    }

}