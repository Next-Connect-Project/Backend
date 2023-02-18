package com.project.unigram.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ResponseError {
	
	private String resultCode;
	private String message;
	
	public ResponseError(String resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}
	
}
