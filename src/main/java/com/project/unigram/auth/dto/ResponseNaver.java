package com.project.unigram.auth.dto;

import com.project.unigram.auth.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseNaver {
	
	private String resultcode;
	
	private String message;
	
	private Member response;
	
}
