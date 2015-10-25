package com.mycom.domain;

import java.io.Serializable;

/**
 * 消息对象
 */
public class Subject implements Serializable{
	private Integer id;
	private String msg;
	public Subject(){}
	
	public Subject(Integer i,String msg){
		this.id = i;
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", msg=" + msg + "]";
	}


}
