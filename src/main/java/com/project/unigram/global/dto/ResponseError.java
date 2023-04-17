package com.project.unigram.global.dto;

import lombok.Getter;

@Getter
public class ResponseError {
	
	private int resultCode;
	private String errorCode;
	private String message;
	
	public ResponseError(ErrorCode errorCode, String message) {
		this.resultCode = errorCode.getResultCode();
		this.errorCode = errorCode.getCode();
		this.message = message;
	}
	
}
