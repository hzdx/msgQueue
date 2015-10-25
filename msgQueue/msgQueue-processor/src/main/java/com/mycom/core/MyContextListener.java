package com.mycom.core;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mycom.process.Processor;

public class MyContextListener implements ServletContextListener {

	private ExecutorService executorService = null;
	
	private List<Processor> processorList = null;

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		executorService = (ExecutorService) ctx.getBean("executorService");
		processorList = (List<Processor>) ctx.getBean("processorList");
		for (Processor processor : processorList) {
			executorService.execute(processor);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		executorService.shutdown();
	}

}
