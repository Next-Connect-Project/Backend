package com.project.unigram.auth.controller;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.security.TokenGenerator;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.global.dto.ResponseSuccess;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final MemberService memberService;
	private final TokenGenerator tokenGenerator;
	
	@ApiOperation(
			value = "사용자 정보 조회",
			notes = "네이버 서버로부터 사용자의 정보를 조회한다."
	)
	@PostMapping("/login/naver")
	public ResponseSuccess login(@RequestBody @Valid RequestAccessToken requestAccessToken) {
		String accessToken = requestAccessToken.getAccessToken();
		Token token = memberService.getToken(accessToken);
		
		return new ResponseSuccess(200, "로그인에 성공했습니다.", new TokenDto(token));
	}
	
	@ApiOperation(
			value = "토큰 재발급",
			notes = "리프레시 토큰을 이용하여 토큰을 재발급 받는다."
	)
	@GetMapping("/reissue")
	public ResponseSuccess reissue() {
		Token token = memberService.reissueToken();
		
		return new ResponseSuccess(200, "토큰 재발급에 성공했습니다.", new TokenDto(token));
	}
	
	@ApiOperation(
		value = "사용자 정보 조회",
	    notes = "에세스 토큰을 이용하여 사용자의 정보를 조회한다."
	)
	@GetMapping("/my")
	public ResponseSuccess userInfo() {
		Member member = memberService.getMember();
		
		MemberDto memberDto = new MemberDto(member.getEmail(), member.getName(), member.getRole());
		
		return new ResponseSuccess(200, "사용자 정보를 불러오는데 성공했습니다.", memberDto);
	}
	
	@Data
	static class RequestAccessToken {
		@NotEmpty(message = "NOT_EMPTY:네이버로 부터 받은 에세스 토큰을 넣어주세요.")
		private String accessToken;
	}
	
	@Data
	@AllArgsConstructor
	static class MemberDto {
		private String email;
		private String name;
		private Role role;
	}
	
	@Data
	static class TokenDto {
		private String accessToken;
		private Date accessExp;
		private String refreshToken;
		private Date refreshExp;
		
		public TokenDto(Token t) {
			this.accessToken = t.getAccessToken();
			this.accessExp = t.getAccessExp();
			this.refreshToken = t.getRefreshToken();
			this.refreshExp = t.getRefreshExp();
		}
	}
	
}
