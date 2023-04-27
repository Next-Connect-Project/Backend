package com.project.unigram.auth.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.dto.NaverAccessTokenDto;
import com.project.unigram.auth.dto.NaverMemberDto;
import com.project.unigram.auth.dto.ResponseNaver;
import com.project.unigram.auth.exception.AuthErrorCode;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.security.TokenGenerator;
import com.project.unigram.global.exception.ServerException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
	
	@Value("${naver.client-id}") private String clientID;
	@Value("${naver.client-secret}") private String clientSecret;
	private final String grantType = "authorization_code";
	private final MemberRepository memberRepository;
	private final TokenGenerator tokenGenerator;
	private final RestTemplate restTemplate;
	
	@Transactional
	public Token getToken(String code) {
		String accessToken = getAccessTokenFromNaver(code);
		Member member = getUserInfoFromNaver(accessToken);
		// 회원가입
		if (memberRepository.findOne(member.getId()) == null) memberRepository.save(member);

		return generateToken(member.getId(), member.getRole());
	}
	
	// 요청 들어올 때마다 항상 재발급
	public Token reissueToken() {
		Member member = getMember();
		
		return generateToken(member.getId(), member.getRole());
	}
	
	// 멤버 정보 가져오기
	public Member getMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = authentication.getName();
		
		return memberRepository.findOne(Long.parseLong(memberId));
	}
	
	private Token generateToken(Long memberId, Role role) {
		// 토큰 생성 후 저장
		Token token = tokenGenerator.getToken(memberId, role);
		
		return token;
	}
	
	private String getAccessTokenFromNaver(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		String uri = UriComponentsBuilder
				.fromHttpUrl("https://nid.naver.com/oauth2.0/token")
				.queryParam("grant_type", grantType)
				.queryParam("client_id", clientID)
				.queryParam("client_secret", clientSecret)
				.queryParam("code", code)
				.encode()
				.toUriString();
		
		ResponseEntity<NaverAccessTokenDto> res = restTemplate.exchange(
				uri,
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<NaverAccessTokenDto>() {
				}
		);
		
		NaverAccessTokenDto dto = res.getBody();
		
		if (dto.getError() != null) throw new ServerException(
				String.format("네이버 서버로부터 접근 토큰을 받는 과정에서 오류가 발생했습니다. code : %s", dto.getError()),
				AuthErrorCode.NAVER_SERVER_ERROR
		);
		
		return res.getBody().getAccess_token();
	}
	
	private Member getUserInfoFromNaver(String accessToken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(accessToken);
			
			ResponseEntity<ResponseNaver> res = restTemplate.exchange(
					"https://openapi.naver.com/v1/nid/me",
					HttpMethod.GET,
					new HttpEntity<>(headers),
					new ParameterizedTypeReference<ResponseNaver>() {
					}
			);
			// Dto로 받은 후 엔티티 변환
			NaverMemberDto naverMemberDto = res.getBody().getResponse();
			
			Member member = Member.builder()
				                .id(naverMemberDto.getId())
				                .name(naverMemberDto.getName())
				                .email(naverMemberDto.getEmail())
				                .build();
			
			return member;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new ServerException("네이버 서버로부터 사용자 정보를 받는 과정에서 오류가 발생했습니다.", AuthErrorCode.NAVER_SERVER_ERROR);
		}
	}
	
}
