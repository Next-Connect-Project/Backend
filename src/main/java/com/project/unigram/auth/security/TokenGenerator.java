package com.project.unigram.auth.security;

import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.domain.Type;
import com.project.unigram.auth.exception.AuthErrorCode;
import com.project.unigram.auth.exception.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Getter
public class TokenGenerator {
	
	private Key key;
	private Long accessExp;
	private Long refreshExp;
	private String[] claimKeys = { "Roles", "Type" };
	
	// yml 파일에서 가져오기
	public TokenGenerator(@Value("${jwt.secret}") String key,
	                      @Value("${jwt.access-token-validaity-in-seconds}") String accessExp,
	                      @Value("${jwt.refresh-token-validaity-in-seconds}") String refreshExp) {
		byte[] keyBytes = key.getBytes();
		this.key = Keys.hmacShaKeyFor(keyBytes);
		// 생성할 때부터 만들면 안됨!! 과거의 시간을 가지고 있는 것임..
		this.accessExp = Long.parseLong(accessExp);
		this.refreshExp = Long.parseLong(refreshExp);
	}
	
	// 토큰 생성하기
	public Token getToken(Long id, Role role) {
		Date accessExp = getExp(this.accessExp);
		Date refreshExp = getExp(this.refreshExp);
		return Token.initToken(id, role, accessExp, refreshExp, key);
	}
	
	// 토큰으로부터 권한 객체 가져오기
	public Authentication getAuthentication(Token token, Type tokenType) {
		Claims claims = token.getClaims(key, tokenType);
		String memberId = claims.getSubject();
		
		// 잘못된 타입의 토큰을 넘겨줬을 때
		if (!claims.get("Type", String.class).equals(tokenType.toString())) throw new TokenInvalidException(String.format("잘못된 타입의 토큰입니다. expected: %s", tokenType), AuthErrorCode.WRONG_TOKEN_TYPE);

		// if (tokenType == Type.RTK) isRightRefreshToken(Long.parseLong(memberId), token.getRefreshToken());
		
		// 모든 claims 가져와서 List로 만듦
		Collection<? extends GrantedAuthority> authority = Arrays.stream(claimKeys)
																.map(k -> new SimpleGrantedAuthority(claims.get(k, String.class)))
		                                                        .collect(Collectors.toList());

		User principal = new User(memberId, "", authority);
		
		return new UsernamePasswordAuthenticationToken(principal, token, authority);
	}

//	private void isRightRefreshToken(Long memberId, String refreshToken) {
//		String redisToken = tokenRepository.findById(memberId);
//		if (!redisToken.equals(refreshToken)) throw new TokenInvalidException("유효한 토큰이지만 해당 유저의 리프레시 토큰이 아닙니다.");
//	}

	// 유효시간 생성
	private Date getExp(Long sec) {
		return new Date(System.currentTimeMillis() + sec);
	}
	
}
