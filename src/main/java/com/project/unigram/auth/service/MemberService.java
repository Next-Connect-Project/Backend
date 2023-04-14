package com.project.unigram.auth.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.dto.NaverMemberDto;
import com.project.unigram.auth.dto.ResponseNaver;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.repository.TokenRepository;
import com.project.unigram.auth.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final TokenRepository tokenRepository;
	private final TokenGenerator tokenGenerator;
	private final RestTemplate restTemplate;
	
	@Transactional
	public Token getToken(String accessToken) {
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
		tokenRepository.save(memberId, token.getRefreshToken(), tokenGenerator.getRefreshExp());
		
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
			// Dto로 받은 후 엔티티 변환
			NaverMemberDto naverMemberDto = res.getBody().getResponse();
			
			Member member = Member.builder()
				                .id(naverMemberDto.getId())
				                .name(naverMemberDto.getName())
				                .email(naverMemberDto.getEmail())
				                .build();
			
			return member;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw e;
		}
	}
	
}
