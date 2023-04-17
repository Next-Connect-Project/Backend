package com.project.unigram.global.exception;

import com.project.unigram.global.dto.ErrorCode;

public class ServerException extends RuntimeException {
	
	private ErrorCode errorCode;
	
	public ServerException(String s, ErrorCode errorCode) {
		super(s);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
	
}
