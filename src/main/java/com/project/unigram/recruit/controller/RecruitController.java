package com.project.unigram.recruit.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.global.dto.ResponseSuccess;
import com.project.unigram.recruit.domain.*;
import com.project.unigram.recruit.dto.RequestRecruitmentDto;
import com.project.unigram.recruit.dto.ResponseRecruitmentDto;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.project.unigram.recruit.repository.RecruitmentRepository;
import com.project.unigram.recruit.service.RecruitmentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/recruit")
@RequiredArgsConstructor
public class RecruitController {
	
	private final RecruitmentService recruitmentService;
	private final RecruitmentRepository recruitmentRepository;
	private final MemberService memberService;

	@PostMapping("/create")
	public ResponseSuccess create(@RequestBody @Valid RequestRecruitmentDto res) {
		Member member = memberService.getMember();
		
		Required required = Required.builder()
				                    .timeandplace(res.getTimeandplace())
				                    .purpose(res.getPurpose())
				                    .duration(res.getDuration())
				                    .progress(res.getProgress())
				                    .contact(res.getContact())
				                    .way(res.getWay())
									.build();
		
		Recruitment recruitment = Recruitment.create(member,
				res.getCategory(),
				res.getTitle(),
				res.getDeadline(),
				res.getTech(),
				res.getPersonnel(),
				required,
				res.getSelected());
		
		Long id = recruitmentService.post(recruitment);
		
		return new ResponseSuccess(200, "저장에 성공했습니다.", new ResponseCreateRecruitment(id));
	}
	
	@GetMapping("/search")
	public ResponseSuccess search(@RequestParam(value = "category", defaultValue = "null") String category,
	                              @RequestParam(value = "state", defaultValue = "null") String state,
	                              @RequestParam(value = "page", defaultValue = "1") int page,
	                              @RequestParam(value = "limit", defaultValue = "16") int limit) {
		
		Category c = category == null ? null : Category.valueOf(category);
		State s = state == null ? null : State.valueOf(state);
		
		RecruitmentSearch recruitmentSearch = new RecruitmentSearch(c, s, page, limit);
		
		List<Recruitment> recruitments = recruitmentRepository.findRecruitmentWithSearch(recruitmentSearch);
		
		List<ResponseRecruitmentDto> responseRecruitmentDtos = recruitments.stream()
													.map(ResponseRecruitmentDto::new)
													.collect(Collectors.toList());
		
		return new ResponseSuccess(200, "모집글 조회에 성공하였습니다.", new ResponseSearchRecruitment(responseRecruitmentDtos));
	}
	
	@PutMapping("/update/{recruitId}")
	public ResponseSuccess update(@PathVariable("recruitId") Long recruitId, @RequestBody @Valid RequestRecruitmentDto requestRecruitmentDto) {
		Member member = memberService.getMember();
		
		recruitmentService.update(recruitId, requestRecruitmentDto, member.getId());
		
		return new ResponseSuccess(200, "모집글을 성공적으로 수정했습니다.", new ResponseRecruitmentId(recruitId));
	}
	
	@DeleteMapping("/delete/{recruitId}")
	public ResponseSuccess delete(@PathVariable("recruitId") Long recruitId) {
		Member member = memberService.getMember();
		
		recruitmentService.delete(recruitId, member.getId());
		
		return new ResponseSuccess(200, "모집글을 성공적으로 삭제하였습니다.", new ResponseRecruitmentId(recruitId));
	}
	
	@PatchMapping("/state/{recruitId}")
	public ResponseSuccess state(@PathVariable("recruitId") Long recruitId) {
		Member member = memberService.getMember();
		
		recruitmentService.state(recruitId, member.getId());
		
		return new ResponseSuccess(200, "성공적으로 모집글의 상태를 수정했습니다.", new ResponseRecruitmentId(recruitId));
	}
	
	@Data
	@AllArgsConstructor
	static class ResponseSearchRecruitment {
		List<ResponseRecruitmentDto> recruitments;
	}
	
	@Data
	@AllArgsConstructor
	static class ResponseRecruitmentId {
		Long id;
	}

	@Data
	@AllArgsConstructor
	static class ResponseCreateRecruitment {
		private Long id;
	}
	
}
