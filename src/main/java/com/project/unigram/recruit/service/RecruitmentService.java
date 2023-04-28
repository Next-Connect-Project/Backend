package com.project.unigram.recruit.service;

import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.project.unigram.recruit.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {
	
	private final RecruitmentRepository recruitmentRepository;
	
	@Transactional
	public Long post(Recruitment recruitment) {
		recruitmentRepository.save(recruitment);
		return recruitment.getId();
	}
	
	@Transactional
	public void close(Long id) {
		Recruitment r = recruitmentRepository.findOne(id);
		r.close();
	}
	
}
