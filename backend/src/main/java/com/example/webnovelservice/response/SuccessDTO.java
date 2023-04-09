package com.example.webnovelservice.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessDTO<T> implements java.io.Serializable {
	private T body;
	private int length = 1;
	private String message = null;
	public SuccessDTO(T body, int length, String message) {
		this.body = body;
		this.length = length;
		this.message = message;
		if (length == 0) {
			if (this.body instanceof List) {
				this.length = ((List) this.body).size();
			}
			if (this.body instanceof Map) {
				this.length = ((Map) this.body).size();
			}
		}
	}
	public SuccessDTO(T body, String message) {
		this.body = body;
		this.message = message;
		if (this.body instanceof List) {
			this.length = ((List) this.body).size();
		}
		if (this.body instanceof Map) {
			this.length = ((Map) this.body).size();
		}
	}
	public SuccessDTO(T body, Integer length) {
		this.body = body;
		this.length = length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public SuccessDTO(T body) {
		this.body = body;
		if (this.body instanceof List) {
			this.length = ((List<?>) this.body).size();
		}
		if (this.body instanceof Map) {
			this.length = ((Map<?,?>) this.body).size();
		}
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
