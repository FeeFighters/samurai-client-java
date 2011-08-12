package com.feefighers.http;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	
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
		return String.valueOf(getStatusCode());
	}
}
