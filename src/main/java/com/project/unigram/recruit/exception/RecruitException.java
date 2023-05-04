package com.project.unigram.recruit.exception;

import com.project.unigram.global.dto.ErrorCode;

public class RecruitException extends RuntimeException {

	private ErrorCode errorCode;
	
	public RecruitException(String msg, ErrorCode errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() { return errorCode; }
	
}
