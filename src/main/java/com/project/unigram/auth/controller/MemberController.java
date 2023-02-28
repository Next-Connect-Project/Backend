package com.project.unigram.auth.controller;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.dto.RequestAccessToken;
import com.project.unigram.auth.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@ApiOperation(
			value = "사용자 정보 조회",
			notes = "네이버 서버로부터 사용자의 정보를 조회한다."
	)
	@PostMapping("/login")
	public ResponseEntity<Token> login(@Valid RequestAccessToken requestAccessToken) {
		String accessToken = requestAccessToken.getAccessToken();
		Token token = memberService.getToken(accessToken);
		
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
}
