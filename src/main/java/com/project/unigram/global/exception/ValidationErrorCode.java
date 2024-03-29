package com.project.unigram.global.exception;

import com.project.unigram.global.dto.ErrorCode;

public enum ValidationErrorCode implements ErrorCode {
	
	NOT_EMPTY(400, "NOT_EMPTY"),
	OUT_OF_RANGE(400, "OUT_OF_RANGE"),
	WRONG_TYPE(400, "WRONG_TYPE"),
	NOT_PAST(400, "NOT_PAST");
	
	private int resultCode;
	private String code;
	
	ValidationErrorCode(int resultCode, String code) {
		this.resultCode = resultCode;
		this.code = code;
	}
	
	@Override
	public int getResultCode() {
		return resultCode;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
}
