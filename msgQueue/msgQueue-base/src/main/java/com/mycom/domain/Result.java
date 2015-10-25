package com.mycom.domain;

import java.io.Serializable;

/**
 * 返回对象
 */
@SuppressWarnings("serial")
public class Result implements Serializable{
	private int id;
	private String msg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
