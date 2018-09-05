package com.demo.plp.po;

/**
 * 消息对象
 * 
 * @author gaoyiyang
 *
 */
public class Message {
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	private String status;
	private String message;
	private Object data;

	public Message(String message){
		this.message = message;
		this.status = SUCCESS;
	}
	public Message(){
		this.message = "请求成功";
		this.status = SUCCESS;
	}
	public Message(String message, String status){
		this.message = message;
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
