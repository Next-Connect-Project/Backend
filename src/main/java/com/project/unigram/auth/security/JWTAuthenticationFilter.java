package com.project.unigram.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.unigram.auth.domain.Token;
import com.project.unigram.auth.domain.Type;
import com.project.unigram.auth.exception.AuthErrorCode;
import com.project.unigram.auth.exception.TokenInvalidException;
import com.project.unigram.global.dto.ErrorCode;
import com.project.unigram.global.dto.ResponseError;
import com.project.unigram.global.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.print.DocFlavor;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	private final TokenGenerator tokenGenerator;
	private ObjectMapper ob = new ObjectMapper();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = getJWTFromRequest(request);
		String uri = request.getRequestURI();
		
		try {
			if (!uri.equals("/api/auth/login/naver") && !StringUtils.hasText(token)) throw new TokenInvalidException("헤더에 토큰을 넣어주세요", AuthErrorCode.NOT_EXISTS);
		} catch (TokenInvalidException e) {
			sendError(response, 401, e.getErrorCode(), e.getMessage());
		}
		
		if (StringUtils.hasText(token)) {
			try {
				Type type = request.getRequestURI().equals("/api/auth/reissue") ? Type.RTK : Type.ATK;
				
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
			} catch (TokenInvalidException e) {
				sendError(response, 401, e.getErrorCode(), e.getMessage());
			} catch (ServerException e) {
				sendError(response, 500, e.getErrorCode(), e.getMessage());
			}
		}
		
		filterChain.doFilter(request, response); // 다음 필터 수행
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
	
	private void sendError(HttpServletResponse response, int status, ErrorCode code, String message) throws IOException {
		OutputStream out = response.getOutputStream();
		
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ResponseError error = new ResponseError(code, message);
		
		ob.writeValue(out, error);
		out.flush();
	}
	
}
