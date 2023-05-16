package com.project.unigram.recruit.repository;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.State;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecruitmentRepositoryTest {
	
	@Autowired
	private RecruitmentRepository recruitmentRepository;
	@Autowired
	private MemberRepository memberRepository;
	
	@BeforeEach
	void setup() {
		Member member = Member
				.builder()
				.id(1L)
				.role(Role.NAVER)
				.build();
		
		memberRepository.save(member);
		
		String[] tech = new String[]{ "Java", "Spring" };
		
		List<Recruitment> list = new ArrayList<>();
		
		for (int i = 0; i < 25; i++) {
			// category : STUDY state : OPEN
			Recruitment recruitment1 = Recruitment.create(member, Category.STUDY, null, null, tech, null, null, null);
			list.add(recruitment1);
			// category : PROJECT state : OPEN
			Recruitment recruitment2 = Recruitment.create(member, Category.PROJECT, null, null, tech, null, null, null);
			list.add(recruitment2);
			// category : STUDY state : CLOSE
			Recruitment recruitment3 = Recruitment.create(member, Category.STUDY, null, null, tech, null, null, null);
			recruitment3.close();
			list.add(recruitment3);
			// category : PROJECT state : CLOSE
			Recruitment recruitment4 = Recruitment.create(member, Category.PROJECT, null, null, tech, null, null, null);
			recruitment4.close();
			list.add(recruitment4);
		}
		
		recruitmentRepository.saveAll(list);
	}
	
	@Test
	@DisplayName("카운트 쿼리 결과 확인")
	@Transactional(rollbackFor = Exception.class)
	void countTest() {
		// given
		RecruitmentSearch recruitmentSearch = new RecruitmentSearch(Category.PROJECT, State.CLOSE, 1, 16);
		
		// when
		Long count = recruitmentRepository.countRecruitmentWithSearch(recruitmentSearch);
		
		// then
		assertThat(count).isEqualTo(25);
	}
	
	@Test
	@DisplayName("필터 조회 결과 확인")
	@Transactional(rollbackFor = Exception.class)
	void filterTest() {
		// given
		RecruitmentSearch recruitmentSearch = new RecruitmentSearch(Category.PROJECT, State.CLOSE, 2, 16);
		
		// when
		List<Recruitment> result = recruitmentRepository.findRecruitmentWithSearch(recruitmentSearch);
		
		// then
		assertThat(result).hasSize(9);
	}
	
}