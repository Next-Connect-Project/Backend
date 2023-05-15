package com.project.unigram.auth.domain;

import com.project.unigram.auth.exception.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class TokenTest {
	
	private @Value("${jwt.secret}") String envKey;
	private @Value("${jwt.access-token-validaity-in-seconds}") String envAccessExp;
	private @Value("${jwt.refresh-token-validaity-in-seconds}") String envRefreshExp;
	private Key key;
	private Long accessExp;
	private Long refreshExp;
	
	@BeforeEach
	public void 테스트_메서드_실행_전_호출() {
		byte[] keyBytes = envKey.getBytes();
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessExp = Long.parseLong(envAccessExp);
		this.refreshExp = Long.parseLong(envRefreshExp);
	}
	
	@Test
	void 에세스_토큰으로부터_정상적으로_claim_가져옴() {
		// given
		Member member = getMember();
		Date accessExp = new Date(System.currentTimeMillis() + this.accessExp);
		Date refreshExp = new Date(System.currentTimeMillis() + this.refreshExp);
		
		// when
		Token token = Token.initToken(member.getId(), Role.NAVER, accessExp, refreshExp, key);
		Claims claims = token.getClaims(key, Type.ATK);
		
		// then
		assertThat(claims).isNotEmpty();
	}
	
	@Test
	@DisplayName("유효하지 않는 에세스 토큰은 TokenInvalidException을 던진다.")
	void 유효하지_않은_에세스_토큰_에러() {
		// given
		Token token = Token.builder()
		                .accessToken("weired_token")
						.build();
		
		// when
		assertThatThrownBy(() -> {
			Claims claims = token.getClaims(key, Type.ATK);
		})
				.isExactlyInstanceOf(TokenInvalidException.class)
				.isInstanceOf(RuntimeException.class)
				.hasMessage("토큰이 유효하지 않습니다.");
	}
	
	@Test
	@DisplayName("유효 기간이 만료된 토큰은 okenInvalidException을 던진다.")
	void 유효_기간이_만료된_에세스_토큰_에러() {
		// given
		Member member = getMember();
		Date accessExp = new Date(System.currentTimeMillis());
		Date refrehExp = new Date(System.currentTimeMillis() + this.refreshExp);
		
		// when
		Token token = Token.initToken(member.getId(), Role.NAVER, accessExp, refrehExp, key);
		
		// then
		assertThatThrownBy(() -> {
			Claims claims = token.getClaims(key, Type.ATK);
		})
				.isExactlyInstanceOf(TokenInvalidException.class)
				.isInstanceOf(RuntimeException.class) // 부모 클래스 검사
				.hasMessage("토큰의 유효 기간이 만료되었습니다.");
	}
	
	@Test
	@DisplayName("에세스 토큰이 null이면 TokenInvalidException을 던진다.")
	void 에세스_토큰이_null_이면_에러() {
		// given
		Token token = Token.builder().build();
		
		// when
		assertThatThrownBy(() -> {
			Claims claims = token.getClaims(key, Type.ATK);
		})
		// then
				.isExactlyInstanceOf(TokenInvalidException.class)
				.isInstanceOf(RuntimeException.class)
				.hasMessage("토큰이 빈 값(null)입니다. 헤더에 토큰을 넣어주세요.");
	}
	
	@Test
	@DisplayName("토큰의 서명이 잘못되면 TokenInvalidException을 던진다.")
	void 에세스_토큰의_서명이_잘못되면_에러() {
		// given
		Member member = getMember();
		Date accessExp = new Date(System.currentTimeMillis() + this.accessExp);
		Date refreshExp = new Date(System.currentTimeMillis() + this.refreshExp);
		Key fakeKey = Keys.hmacShaKeyFor("is_wrong_key_for_test_over_512_bits_too_hard_to_increase_length_of_string".getBytes());
		
		// when
		Token token = Token.initToken(member.getId(), Role.NAVER, accessExp, refreshExp, key);
		assertThatThrownBy(() -> {
			Claims claims = token.getClaims(fakeKey, Type.ATK);
		})
		// then
				.isExactlyInstanceOf(TokenInvalidException.class)
				.isInstanceOf(RuntimeException.class)
				.hasMessage("토큰의 서명이 유효하지 않습니다.");
	}
	
	private Member getMember() {
		return Member.builder()
				.id(1L)
				.build();
	}
	
}