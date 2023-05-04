package com.project.unigram.auth.exception;

import com.project.unigram.global.dto.ErrorCode;
import org.springframework.security.access.AccessDeniedException;

public class TokenInvalidException extends AccessDeniedException {
	
	private ErrorCode errorCode;
	
	public TokenInvalidException(String s, ErrorCode errorCode) {
		super(s);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
}
