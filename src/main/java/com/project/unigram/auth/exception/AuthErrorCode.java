package com.project.unigram.auth.exception;

import com.project.unigram.global.dto.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
	
	INVALIED_TOKEN(401, "INVALIED_TOKEN"),
	WRONG_TOKEN_TYPE(401, "WRONG_TOKEN_TYPE"),
	NOT_EXISTS(401, "NOT_EXISTS"),
	
	NAVER_SERVER_ERROR(500, "NAVER_SERVER_ERROR");
	
	private int resultCode;
	private String code;
	
	AuthErrorCode(int resultCode, String code) {
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
