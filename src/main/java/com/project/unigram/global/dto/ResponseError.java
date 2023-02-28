package com.project.unigram.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ResponseError {
	
	private int resultCode;
	private String message;
	
	public ResponseError(int resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}
	
}
