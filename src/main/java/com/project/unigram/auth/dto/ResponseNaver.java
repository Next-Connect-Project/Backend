package com.project.unigram.auth.dto;

import com.project.unigram.auth.domain.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ResponseNaver {
	
	private String resultcode;
	
	private String message;
	
	// Dto는 엔티티를 의존해서는 안된다.
	private NaverMemberDto response;
	
}
