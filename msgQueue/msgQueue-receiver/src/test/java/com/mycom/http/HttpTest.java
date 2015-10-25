package com.mycom.http;

import com.mycom.domain.Subject;
import com.mycom.util.Codec;
import com.mycom.util.HttpUtil;

public class HttpTest {

	public static void main(String[] args) throws Exception {
		String url = "http://localhost:8080/msgQueue-receiver/server";
		Subject sub = new Subject(178,"ddd");
		//String msg = "{id:5;msg:\"abc\"}";
		String msg = Codec.toJson(sub);
		System.out.println("json string:"+msg);
		Subject sub1 = Codec.fromJson(msg, Subject.class);
		System.out.println("subject is:"+sub);
		HttpUtil.postForm(url, msg);

	}

}
