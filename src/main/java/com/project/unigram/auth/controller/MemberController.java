package com.project.unigram.auth.controller;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.dto.RequestAccessToken;
import com.project.unigram.auth.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/auth")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@ApiOperation(
			value = "사용자 정보 조회",
			notes = "네이버 서버로부터 사용자의 정보를 조회한다."
	)
	@PostMapping("/my")
	public ResponseEntity<Member> getUserInfo(@Valid RequestAccessToken requestAccessToken) {
		String accessToken = requestAccessToken.getAccessToken();
		Member member = memberService.getUserInfoFromNaver(accessToken);
		
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
}
