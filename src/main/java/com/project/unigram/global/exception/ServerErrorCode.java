package com.project.unigram.global.exception;

import com.project.unigram.global.dto.ErrorCode;

public enum ServerErrorCode implements ErrorCode {
	
	INTERNAL_SERVER(500, "INTERNAL_SERVER");
	
	private int resultCode;
	private String code;
	
	ServerErrorCode(int resultCode, String code) {
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
