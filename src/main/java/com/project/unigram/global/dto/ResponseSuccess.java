package com.project.unigram.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseSuccess<T> {
	
	private int resultCode;
	private String message;
	private T response;
	
}
