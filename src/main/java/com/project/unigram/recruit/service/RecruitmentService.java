package com.project.unigram.recruit.service;

import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.Required;
import com.project.unigram.recruit.domain.State;
import com.project.unigram.recruit.dto.RequestRecruitmentDto;
import com.project.unigram.recruit.exception.RecruitErrorCode;
import com.project.unigram.recruit.exception.RecruitException;
import com.project.unigram.recruit.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	public void state(Long recruitId, Long memberId) {
		Recruitment r = recruitmentRepository
							.findById(recruitId)
							.orElseThrow(() -> new RecruitException("해당되는 아이디의 게시글이 없습니다.", RecruitErrorCode.WRONG_ID));
		
		if (!r.isAuthorizedMember(memberId)) throw new RecruitException("해당 글에 수정 권한이 없는 사용자입니다.", RecruitErrorCode.NOT_OWNER);
		
		if (r.getState() == State.OPEN) r.close();
		else r.open();
	}
	
	@Transactional
	public void update(Long recruitId, RequestRecruitmentDto requestRecruitmentDto, Long memberId) {
		Recruitment r = recruitmentRepository
								.findById(recruitId)
								.orElseThrow(() -> new RecruitException("해당되는 아이디의 게시글이 없습니다.", RecruitErrorCode.WRONG_ID));
		
		if (!r.isAuthorizedMember(memberId)) throw new RecruitException("해당 글에 수정 권한이 없는 사용자입니다.", RecruitErrorCode.NOT_OWNER);
		
		Required required = Required.builder()
				.contact(requestRecruitmentDto.getContact())
				.progress(requestRecruitmentDto.getProgress())
				.duration(requestRecruitmentDto.getDuration())
				.purpose(requestRecruitmentDto.getPurpose())
				.timeandplace(requestRecruitmentDto.getTimeandplace())
				.build();
		
		List<String> tech = Arrays.stream(requestRecruitmentDto.getTech()).collect(Collectors.toList());
		
		r.updateAll(Category.valueOf(requestRecruitmentDto.getCategory()),
				requestRecruitmentDto.getTitle(),
				requestRecruitmentDto.getDeadline(),
				tech,
				requestRecruitmentDto.getPersonnel(),
				required,
				requestRecruitmentDto.getFree());
	}
	
	@Transactional
	public void delete(Long recruitId, Long memberId) {
		Recruitment r = recruitmentRepository
							.findById(recruitId)
							.orElseThrow(() -> new RecruitException("해당되는 아이디의 게시글이 없습니다.", RecruitErrorCode.WRONG_ID));
		
		if (!r.isAuthorizedMember(memberId)) throw new RecruitException("해당 글에 삭제 권한이 없는 사용자입니다.", RecruitErrorCode.NOT_OWNER);
		
		recruitmentRepository.delete(r);
	}
	
}
