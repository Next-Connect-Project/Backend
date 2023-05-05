package com.project.unigram.auth.exception;

import com.project.unigram.global.dto.ErrorCode;

public class TokenInvalidException extends RuntimeException {
	
	private ErrorCode errorCode;

	public TokenInvalidException(String s, ErrorCode errorCode) {
		super(s);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
}
