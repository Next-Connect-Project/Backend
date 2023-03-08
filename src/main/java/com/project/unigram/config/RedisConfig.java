package com.project.unigram.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

	private final RedisProperties redisProperties;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		// LettuceConnectionFactory는 RedisConnectionFactory의 구현체
		return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
	}
	
	@Bean
	public RedisTemplate<Long, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Long, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
	
}
