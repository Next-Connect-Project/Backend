package com.project.unigram.auth.dto;

import lombok.Data;

@Data
public class NaverAccessTokenDto {
	
	private String access_token;
	private String error;
	
}
