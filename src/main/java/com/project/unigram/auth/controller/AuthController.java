package com.project.unigram.auth.controller;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.global.dto.ResponseSuccess;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private MemberService memberService;
	private String domain;
	private int refreshExp;
	
	public AuthController(MemberService memberService,
	                      @Value("${jwt.cookie-domain}") String domain,
	                      @Value("${jwt.refresh-token-validaity-in-seconds}") int refreshExp) {
		this.memberService = memberService;
		this.domain = domain;
		this.refreshExp = refreshExp;
	}
	
	@ApiOperation(
			value = "사용자 정보 조회",
			notes = "네이버 서버로부터 사용자의 정보를 조회한다."
	)
	@PostMapping("/login/naver")
	public ResponseSuccess login(@RequestBody @Valid RequestCode requestCode, HttpServletResponse res) {
		String code = requestCode.getCode();
		Token token = memberService.getToken(code);
		
		Cookie cookie = new Cookie("refreshToken", token.getRefreshToken());
		cookie.setDomain(domain);
		cookie.setMaxAge(refreshExp); // 유효기간 2달
		cookie.setPath("/"); // 모든 경로에서 쿠키 set
		cookie.setHttpOnly(true); // httpOnly로 설정
		res.addCookie(cookie);
		
		return new ResponseSuccess(200, "로그인에 성공했습니다.", new TokenDto(token));
	}
	
	@GetMapping("/logout")
	public ResponseSuccess logout(HttpServletResponse res) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setDomain(domain);
		cookie.setMaxAge(0);
		cookie.setPath("/"); // 모든 경로에서 쿠키 set
		cookie.setHttpOnly(true);
		res.addCookie(cookie);
		
		return new ResponseSuccess(200, "로그아웃에 성공했습니다.", null);
	}
	
	@ApiOperation(
			value = "토큰 재발급",
			notes = "리프레시 토큰을 이용하여 토큰을 재발급 받는다."
	)
	@GetMapping("/reissue")
	public ResponseSuccess reissue(HttpServletResponse res) {
		Token token = memberService.reissueToken();
		
		Cookie cookie = new Cookie("refreshToken", token.getRefreshToken());
		cookie.setDomain(domain);
		cookie.setMaxAge(refreshExp); // 유효기간 2달
		cookie.setPath("/"); // 모든 경로에서 쿠키 set
		cookie.setHttpOnly(true); // httpOnly로 설정
		res.addCookie(cookie);
		
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
	static class RequestCode {
		@NotEmpty(message = "NOT_EMPTY:네이버로 부터 받은 인증 코드를 넣어주세요.")
		private String code;
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
		
		public TokenDto(Token t) {
			this.accessToken = t.getAccessToken();
			this.accessExp = t.getAccessExp();
		}
	}
	
}
