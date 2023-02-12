package com.project.unigram.auth.service;

import com.project.unigram.auth.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

	@Autowired MemberService memberService;
	
	@Test(expected = HttpClientErrorException.class)
	public void 네이버로부터_에러() {
		Member member = memberService.getUserInfoFromNaver(null);
		
		fail("네이버 서버로부터 401에러 응답");
	}

}