package com.project.unigram.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRepository {

	private final RedisTemplate<Long, String> redisTemplate;
	
	public void save(Long memberId, String refershToken, Long timeout) {
		ValueOperations<Long, String> longStringValueOperations = redisTemplate.opsForValue();
		longStringValueOperations.set(memberId, refershToken, timeout, TimeUnit.MILLISECONDS);
	}
	
	public String findById(Long memberId) {
		ValueOperations<Long, String> longStringValueOperations = redisTemplate.opsForValue();
		return longStringValueOperations.get(memberId);
	}

}
