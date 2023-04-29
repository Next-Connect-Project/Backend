package com.project.unigram.recruit.service;

import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.State;
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
	public void state(Long id) {
		Recruitment r = recruitmentRepository.findOne(id);
		if (r.getState() == State.OPEN) r.close();
		else r.open();
	}
	
	@Transactional
	public void delete(Recruitment r) {
		recruitmentRepository.deleteOne(r);
	}
	
}
