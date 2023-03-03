package com.project.unigram.auth.domain;

import com.project.unigram.auth.exception.TokenInvalidException;
import com.project.unigram.global.exception.ServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.rmi.ServerError;
import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTest {
	
	private @Value("${jwt.secret}") String envKey;
	private @Value("${jwt.access-token-validaity-in-seconds}") String envAccessExp;
	private @Value("${jwt.refresh-token-validaity-in-seconds}") String envRefreshExp;
	private Key key;
	private Long accessExp;
	private Long refreshExp;
	
	@Before
	public void 테스트_메서드_실행_전_호출() {
		byte[] keyBytes = envKey.getBytes();
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessExp = Long.parseLong(envAccessExp);
		this.refreshExp = Long.parseLong(envRefreshExp);
	}
	
	@Test
	public void 에세스_토큰으로부터_정상적으로_claim_가져옴() {
		// given
		Member member = getMember();
		Date accessExp = new Date(System.currentTimeMillis() + this.accessExp);
		Date refreshExp = new Date(System.currentTimeMillis() + this.refreshExp);
		
		// when
		Token token = Token.initToken(member.getId(), Role.NAVER, accessExp, refreshExp, key);
		Claims claims = token.getClaims(key);
		
		// then
		assertThat(claims).isNotEmpty();
	}
	
	@Test(expected = TokenInvalidException.class)
	public void 유효하지_않은_에세스_토큰_에러() {
		// given
		Token token = Token.builder()
		                .accessToken("weired_token")
						.build();
		
		// when
		Claims claims = token.getClaims(key);
		
		// then
		fail("유효하지 않은 토큰 에러 발생");
	}
	
	@Test(expected = TokenInvalidException.class)
	public void 유효_기간이_만료된_에세스_토큰_에러() {
		// given
		Member member = getMember();
		Date accessExp = new Date(System.currentTimeMillis());
		Date refrehExp = new Date(System.currentTimeMillis() + this.refreshExp);
		
		// when
		Token token = Token.initToken(member.getId(), Role.NAVER, accessExp, refrehExp, key);
		Claims claims = token.getClaims(key);
	
		// then
		fail("에세스 토큰의 기간 만료 에러 발생");
	}
	
	@Test(expected = TokenInvalidException.class)
	public void 에세스_토큰이_null_이면_에러() {
		// given
		Token token = Token.builder().build();
		
		// when
		Claims claims = token.getClaims(key);
	
		// then
		fail("에세스 토큰이 비었으면 에러 발생");
	}
	
	@Test(expected = ServerException.class)
	public void 에세스_토큰의_서명이_잘못되면_에러() {
		// given
		Member member = getMember();
		Date accessExp = new Date(System.currentTimeMillis() + this.accessExp);
		Date refreshExp = new Date(System.currentTimeMillis() + this.refreshExp);
		Key fakeKey = Keys.hmacShaKeyFor("is_wrong_key_for_test_over_512_bits_too_hard_to_increase_length_of_string".getBytes());
		
		// when
		Token token = Token.initToken(member.getId(), Role.NAVER, accessExp, refreshExp, key);
		Claims claims = token.getClaims(fakeKey);
		
		// then
		fail("서명이 유효하지 않으면 에러 발생");
	}
	
	private Member getMember() {
		return Member.builder()
				.id(1L)
				.build();
	}
	
}