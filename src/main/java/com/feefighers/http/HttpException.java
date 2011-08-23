package com.feefighers.http;

import org.apache.commons.lang.StringUtils;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Integer statusCode;
	
	public HttpException(String message) {
		super(message);
	}
	
	public HttpException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
	
	public HttpException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}	
	
	public HttpException(Throwable ex) {
		super(ex);
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	@Override
	public String getMessage() {
		String superMessage = StringUtils.defaultString(super.getMessage());
		if(statusCode != null) {
			superMessage += "(status code: " + statusCode + ")";
		}
		return superMessage;
	}
}
