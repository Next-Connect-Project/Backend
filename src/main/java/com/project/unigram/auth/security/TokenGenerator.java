package com.project.unigram.auth.security;

import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
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

@Component
public class TokenGenerator {
	
	private Key key;
	private Long accessExp;
	private Long refreshExp;
	
	// yml 파일에서 가져오기
	public TokenGenerator(@Value("${jwt.secret}") String key,
	                      @Value("${jwt.access-token-validaity-in-seconds}") String accessExp,
	                      @Value("${jwt.refresh-token-validaity-in-seconds}") String refreshExp) {
		byte[] keyBytes = key.getBytes();
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessExp = Long.parseLong(accessExp);
		this.refreshExp = Long.parseLong(refreshExp);
	}
	// 토큰 생성하기
	public Token getToken(Long id, Role role) {
		Date accessExp = getExp(this.accessExp);
		Date refreshExp = getExp(this.refreshExp);
		return Token.initToken(id, role, accessExp, refreshExp, key);
	}
	
	// 유효시간 생성
	public Date getExp(Long sec) {
		return new Date(System.currentTimeMillis() + sec);
	}
	
	// 토큰으로부터 권한 객체 가져오기
	public Authentication getAuthentication(Token token) {
		Claims claims = token.getClaims(key);
		Collection<? extends GrantedAuthority> authority = Arrays.asList(new SimpleGrantedAuthority(claims.get("Roles", String.class)));
		
		User principal = new User(claims.getSubject(), "", authority);
		
		return new UsernamePasswordAuthenticationToken(principal, token, authority);
	}
	
}
