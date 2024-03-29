package com.project.unigram.recruit.controller;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.global.dto.ResponseSuccess;
import com.project.unigram.recruit.domain.*;
import com.project.unigram.recruit.dto.RequestRecruitmentDto;
import com.project.unigram.recruit.dto.ResponseDetailRecruitmentDto;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.project.unigram.recruit.dto.ResponseSimpleRecruitmentDto;
import com.project.unigram.recruit.exception.RecruitErrorCode;
import com.project.unigram.recruit.exception.RecruitException;
import com.project.unigram.recruit.repository.RecruitmentRepository;
import com.project.unigram.recruit.service.RecruitmentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
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
				Category.valueOf(res.getCategory()),
				res.getTitle(),
				res.getDeadline(),
				res.getTech(),
				res.getPersonnel(),
				required,
				res.getFree());
		
		Long id = recruitmentService.post(recruitment);
		
		return new ResponseSuccess(200, "저장에 성공했습니다.", new ResponseCreateRecruitment(id));
	}
	
	@GetMapping("/search")
	public ResponseSuccess search(@RequestParam(value = "category", required = false) @Pattern(regexp = "PROJECT|STUDY", message = "WRONG_TYPE:PROJECT 혹은 STUDY로 넣어주세요") Category category,
	                              @RequestParam(value = "state", required = false) @Pattern(regexp = "OPEN|CLOSE", message = "WRONG_TYPE:OPEN 혹은 CLOSE로 넣어주세요") State state,
	                              @RequestParam(value = "page", defaultValue = "1") int page,
	                              @RequestParam(value = "limit", defaultValue = "16") int limit) {
		RecruitmentSearch recruitmentSearch = new RecruitmentSearch(category, state, page, limit);
		
		Long count = recruitmentRepository.countRecruitmentWithSearch(recruitmentSearch);
		
		List<Recruitment> recruitments = recruitmentRepository.findRecruitmentWithSearch(recruitmentSearch);
		List<ResponseSimpleRecruitmentDto> responseDetailRecruitmentDtos = recruitments.stream()
													.map(ResponseSimpleRecruitmentDto::new)
													.collect(Collectors.toList());
		
		return new ResponseSuccess(200, "모집글 조회에 성공하였습니다.", new ResponseSearchRecruitment(count, responseDetailRecruitmentDtos));
	}
	
	@GetMapping("/main")
	public ResponseSuccess main() {
		List<Recruitment> recruitment = recruitmentRepository.findFastDeadlineRecruiment();
		
		List<ResponseSimpleRecruitmentDto> responseSimpleRecruitmentDtos = recruitment.stream()
																				.map(ResponseSimpleRecruitmentDto::new)
																				.collect(Collectors.toList());
		
		return new ResponseSuccess(200, "마감일이 임박한 모집글 조회에 성공하였습니다.", new ResponseSimpleRecruitmentList(responseSimpleRecruitmentDtos));
	}
	
	@GetMapping("/my")
	public ResponseSuccess my() {
		Member member = memberService.getMember();
		
		List<Recruitment> recruitment = recruitmentRepository.findByMemberOrderByCreatedAtAsc(member);
		
		List<ResponseSimpleRecruitmentDto> responseSimpleRecruitmentDtos = recruitment.stream()
																					.map(ResponseSimpleRecruitmentDto::new)
																					.collect(Collectors.toList());
		
		return new ResponseSuccess(200, "사용자가 작성한 모집글 조회에 성공했습니다.", new ResponseSimpleRecruitmentList(responseSimpleRecruitmentDtos));
	}
	
	@GetMapping("/detail/{recruitId}")
	public ResponseSuccess detail(@PathVariable("recruitId") Long recruitId) {
		Member member = memberService.getMember();
		
		Recruitment recruitment = recruitmentRepository
										.findById(recruitId)
			                            .orElseThrow(() -> new RecruitException("해당되는 아이디의 게시글이 없습니다.", RecruitErrorCode.WRONG_ID));
		
		boolean owner = false;
		if (member != null) owner = recruitment.isAuthorizedMember(member.getId());
		
		ResponseDetailRecruitmentDto dto = new ResponseDetailRecruitmentDto(recruitment, owner);
		
		return new ResponseSuccess(200, "모집글 조회에 성공하였습니다.", dto);
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
		Long count;
		List<ResponseSimpleRecruitmentDto> recruitments;
	}
	
	@Data
	@AllArgsConstructor
	static class ResponseSimpleRecruitmentList {
		List<ResponseSimpleRecruitmentDto> recruitments;
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
