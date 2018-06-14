package com.ly.httplib.core;
/**
 * 这个类和具体的业务api 结构有关，本Demo的API 结构大致如下：
 *
 * Created by anylife.ly@gmail.com on 2016/7/11.
 */
public class HttpResponse<T> {
	private int code;
	private String message;
	private T result;    //泛型T来表示object，可能是数组，也可能是对象

	public int getCode() {
		return code;
	}

	public boolean isSuccess() {
		if(code==0){
			return true;
		}else {
			return false;
		}
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "httpResponse{" +
				"code=" + code +
				", error='" + message + '\'' +
				", result=" + result +
				'}';
	}
}
