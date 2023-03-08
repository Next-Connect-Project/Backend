package com.project.unigram.config;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {
	
	@Autowired
	RedisTemplate<Long, String> redisTemplate;
	
	@Test
	public void 레디스_연결_정상() {
		assertThat(redisTemplate).isNotNull();
	}
	
	@Test
	public void 레디스_동작_정상() {
		// given
		ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(1L, "value");
		
		// when
		String value = valueOperations.get(1L);
		
		// then
		assertThat(value).isEqualTo("value");
		redisTemplate.delete(1L);
	}
	
	@Test
	public void 레디스_자동_삭제() throws InterruptedException {
		// given
		ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(1L, "value", 1L, TimeUnit.MILLISECONDS);
		
		Thread.sleep(1L);
		
		// when
		String value = valueOperations.get(1L);
		
		// then
		assertThat(value).isNull();
	}
	
}