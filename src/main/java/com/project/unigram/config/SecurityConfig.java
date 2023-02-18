package com.project.unigram.config;

import com.project.unigram.auth.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private TokenGenerator tokenGenerator;

	@Autowired
	public SecurityConfig(TokenGenerator tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/auth/**").authenticated()
				.and()
				.httpBasic();
		return http.build();
	}

}
