package com.project.unigram.auth.domain;

import com.project.unigram.auth.exception.TokenInvalidException;
import com.project.unigram.global.exception.ServerException;
import io.jsonwebtoken.*;
;
import io.jsonwebtoken.security.SignatureException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {
	
	private String accessToken;
	private String refreshToken;
	
	@Builder
	public Token(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	// 토큰 초기화
	public static Token initToken(Long id, Role role, Date accessExp, Date refreshExp, Key key) {
		Token token = new Token();
		token.createAccessToken(id, role, accessExp, key);
		token.createRefreshToken(refreshExp, key);
		return token;
	}
	
	public void createAccessToken(Long id, Role role, Date exp, Key key) {
		this.accessToken = Jwts.builder()
								.setSubject(id.toString()) // 소셜 아이디
								.setExpiration(exp) // 유효시간
								.claim("Roles", role) // 권한
								.signWith(key, SignatureAlgorithm.HS512) // 암호화
								.compact();
	}
	
	public void createRefreshToken(Date exp, Key key) {
		this.refreshToken = Jwts.builder()
								.setExpiration(exp)
								.signWith(key, SignatureAlgorithm.HS512)
								.compact();
	}
	
	public Claims getClaims(Key key) {
		try {
			return Jwts.parserBuilder()
				       .setSigningKey(key)
				       .build()
					   .parseClaimsJws(accessToken)
				       .getBody();
		} catch (ExpiredJwtException e) {
			log.error("에세스 토큰의 유효 기간이 만료되었습니다.");
			throw new TokenInvalidException("에세스 토큰의 유효 기간이 만료되었습니다.");
		} catch (MalformedJwtException e) {
			log.error("에세스 토큰이 유효하지 않습니다");
			throw new TokenInvalidException("에세스 토큰이 유효하지 않습니다.");
		} catch (IllegalArgumentException e) {
			log.error("에세스 토큰이 빈 값(null)입니다.");
			throw new TokenInvalidException("헤더에 에세스 토큰을 넣어주세요.");
		} catch (SignatureException e) {
			log.error("에세스 토큰의 서명이 유효하지 않습니다.");
			throw new ServerException("에세스 토큰의 서명이 유효하지 않습니다.");
		}  catch (UnsupportedJwtException e) {
			log.error("지원하지 않는 에세스 토큰입니다.");
			throw new ServerException("지원하지 않는 에세스 토큰입니다.");
		}
	}
	
}
