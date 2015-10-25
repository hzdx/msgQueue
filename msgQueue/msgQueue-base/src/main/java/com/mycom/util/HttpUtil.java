package com.mycom.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	/**
	 * post方式发生请求
	 */
	public static void postForm(String url,String msg) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity entity = new StringEntity(msg, "UTF-8");
			httppost.setEntity(entity);
			System.out.println("executing request " + httppost.getURI());
			System.out.println("post data:" + EntityUtils.toString(entity));
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity respentity = response.getEntity();
				if (respentity != null) {
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ EntityUtils.toString(respentity, "UTF-8"));
					System.out
							.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
