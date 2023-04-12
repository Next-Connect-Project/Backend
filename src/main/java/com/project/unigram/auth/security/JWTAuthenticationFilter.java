package com.project.unigram.auth.security;

import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.domain.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	private final TokenGenerator tokenGenerator;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = getJWTFromRequest(request);
		if (StringUtils.hasText(token)) {
			Type type = request.getContextPath().equals("/api/v1/auth/token") ? Type.RTK : Type.ATK;
			
			Token authToken = null;
			if (type == Type.ATK) {
				authToken = Token.builder()
							.accessToken(token)
							.build();
			} else {
				authToken = Token.builder()
							.refreshToken(token)
							.build();
			}
			
			Authentication authentication = tokenGenerator.getAuthentication(authToken, type);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			filterChain.doFilter(request, response); // 다음 필터 수행
		}
	}
	
	// 헤더에서 토큰을 가져온다.
	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// 토큰 존재하면 return
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
}