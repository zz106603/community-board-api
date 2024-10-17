package com.spring.blog.common.model.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private boolean isSuccess;
	private String detailMessage;
	
	public ErrorResponse(boolean isSuccess, String detailMessage) {
		this.isSuccess = isSuccess;
		this.detailMessage = detailMessage;
	}
}
