package com.mycom.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mycom.handler.MyHandler;

@SuppressWarnings("serial")
public class MyServlet extends HttpServlet {

	private Logger logger = LoggerFactory.getLogger(MyServlet.class);

	private MyHandler handler = null;

	public MyServlet() {
	}

	public void init(ServletConfig config) throws ServletException {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
		handler = ctx.getBean(MyHandler.class);
	}

	public void destroy() {
		handler = null;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpGet httpGet = (request.getQueryString() == null) ? new HttpGet(
					request.getRequestURI()) : new HttpGet(
					request.getRequestURI() + '?' + request.getQueryString());
			logger.debug("Receive {}", httpGet);
			String msg = "不支持Get方法";
			byte[] b = msg.getBytes("UTF-8");
			response.setContentLength(b.length);
			response.getOutputStream().write(b);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		response.flushBuffer();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpPost httpPost = (request.getQueryString() == null) ? new HttpPost(
					request.getRequestURI()) : new HttpPost(
					request.getRequestURI() + '?' + request.getQueryString());
			httpPost.setEntity(new InputStreamEntity(request.getInputStream()));
			logger.debug("Receive {}", httpPost);
			String str = handler.processPost(httpPost);
			if (str != null) {
				byte[] b = str.getBytes("UTF-8");
				// response.setContentType(httpEntity.getContentType().getValue());
				response.setContentLength(b.length);
				response.getOutputStream().write(b);

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		response.flushBuffer();
	}

}