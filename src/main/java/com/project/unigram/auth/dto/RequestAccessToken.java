package com.project.unigram.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class RequestAccessToken {

	@NotEmpty(message = "네이버로 부터 받은 에세스 토큰을 넣어주세요.")
	private String accessToken;
	
}
