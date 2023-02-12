package com.project.unigram.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseError {
	
	private String resultCode;
	private String message;
	
}
