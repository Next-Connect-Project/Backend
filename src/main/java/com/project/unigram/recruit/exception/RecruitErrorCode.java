package com.project.unigram.recruit.exception;

import com.project.unigram.global.dto.ErrorCode;

public enum RecruitErrorCode implements ErrorCode {
	
	NOT_OWNER(400, "NOT_OWNER"),
	WRONG_ID(400, "WRONG_ID");
	
	private int resultCode;
	private String code;
	
	RecruitErrorCode(int resultCode, String code) {
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
