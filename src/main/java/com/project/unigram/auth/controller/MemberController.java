package com.project.unigram.auth.controller;

import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.security.TokenGenerator;
import com.project.unigram.auth.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final TokenGenerator tokenGenerator;
	
	@ApiOperation(
			value = "사용자 정보 조회",
			notes = "네이버 서버로부터 사용자의 정보를 조회한다."
	)
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody @Valid RequestAccessToken requestAccessToken) {
		String accessToken = requestAccessToken.getAccessToken();
		Token token = memberService.getToken(accessToken);
		Date accessExp = tokenGenerator.getAccessExp();
		Date refreshExp = tokenGenerator.getRefreshExp();
		
		TokenDto tokenDto = new TokenDto(token, accessExp, refreshExp);
		
		return new ResponseEntity<>(tokenDto, HttpStatus.OK);
	}
	
	@Data
	static class RequestAccessToken {
		@NotEmpty(message = "네이버로 부터 받은 에세스 토큰을 넣어주세요.")
		private String accessToken;
	}
	
	@Data
	static class TokenDto {
		private String accessToken;
		private Date accessExp;
		private String refreshToken;
		private Date refreshExp;
		
		public TokenDto(Token t, Date accessExp, Date refreshExp) {
			this.accessToken = t.getAccessToken();
			this.accessExp = accessExp;
			this.refreshToken = t.getRefreshToken();
			this.refreshExp = refreshExp;
		}
	}
	
}
