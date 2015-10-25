package com.mycom.handler;

import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.domain.Subject;
import com.mycom.util.Codec;

public class MyHandler {
	private Logger logger = LoggerFactory.getLogger(MyHandler.class);
	
	@Resource
	private BlockingQueue<Subject> subjectQueue = null;
	
	public HttpResponse processGet(HttpGet get){
		//String msg = EntityUtils.toString(get.get, defaultCharset)
		return null;
	}
	
	public String processPost(HttpPost post){
		
		try {
			String msg = EntityUtils.toString(post.getEntity(), "UTF-8");
			Subject sub = Codec.fromJson(msg,Subject.class);
			subjectQueue.add(sub);
			String result = "添加成功";
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "添加失败";
		}
	}

//	private <T> T  fromJson(String str,Class<T> type) throws Exception{
//		return mapper.readValue(str, type);
//	}
	
}
