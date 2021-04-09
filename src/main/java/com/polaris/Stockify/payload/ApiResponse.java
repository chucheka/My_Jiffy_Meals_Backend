package com.polaris.Stockify.payload;

public class ApiResponse {
	
	private String status;
	
	private Object result;

	
	
	public ApiResponse() {
		super();
	}

	public ApiResponse(String status, Object result) {
		super();
		this.status = status;
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
