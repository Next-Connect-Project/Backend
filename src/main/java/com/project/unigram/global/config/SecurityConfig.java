package com.project.unigram.global.config;

import com.project.unigram.auth.security.JWTAuthenticationFilter;
import com.project.unigram.auth.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final TokenGenerator tokenGenerator;
	
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().antMatchers(
				/* swagger v2 */
				"/v2/api-docs",
				"/swagger-resources",
				"/swagger-resources/**",
				"/configuration/ui",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**",
				/* swagger v3 */
				"/v3/api-docs/**",
				"/swagger-ui/**"
		);
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors().configurationSource(corsConfigurationSource())
				.and()
				.csrf().disable()
				.httpBasic().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api/auth/login/naver", "/api/recruit/search", "/api/recruit/detail/**").permitAll()
				.anyRequest().authenticated();
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter(tokenGenerator);
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		// 스프링 부트 2.4.0부터 allowCredentials가 true일 때 allowedOrigins에 특수 값인 "*" 추가할 수 없음
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
}
