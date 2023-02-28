package com.project.unigram.auth.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.dto.ResponseNaver;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final TokenGenerator tokenGenerator;
	private final RestTemplate restTemplate;
	
	@Transactional
	public Token getToken(String accessToken) {
		Member member = getUserInfoFromNaver(accessToken);
		// 회원가입
		if (memberRepository.findOne(member.getId()) == null) memberRepository.save(member);
		Token token = tokenGenerator.getToken(member.getId(), Role.NAVER);
		return token;
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
			
			Member member = res.getBody().getResponse();
			
			return member;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw e;
		}
	}
	
	
	
}
