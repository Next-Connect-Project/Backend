package com.project.unigram.recruit.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.global.dto.ResponseSuccess;
import com.project.unigram.recruit.domain.*;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.project.unigram.recruit.repository.RecruitmentRepository;
import com.project.unigram.recruit.service.RecruitmentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
		
		PersonnelDto personnelDto = res.getPersonnel();
		Personnel personnel = new Personnel(personnelDto.getBack(), personnelDto.getFront(), personnelDto.getDesign(), personnelDto.getPm(), personnelDto.getOther());
		
		Required required = new Required(res.getPurpose(), res.getTime(), res.getContact(), res.getProcess());
		
		Recruitment recruitment = Recruitment.create(member,
				res.getCategory(),
				res.getDueDate(),
				res.getSkill(),
				personnel,
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
		
		List<RecruitmentDto> recruitmentDtos = recruitments.stream()
													.map(RecruitmentDto::new)
													.collect(Collectors.toList());
		
		return new ResponseSuccess(200, "모집글 조회에 성공하였습니다.", new ResponseSearchRecruitment(recruitmentDtos));
	}
	
	@DeleteMapping("/delete/{recruitId}")
	public ResponseSuccess delete(@PathVariable("recruitId") Long recruitId) {
		Recruitment r = recruitmentRepository.findOne(recruitId);
		recruitmentService.delete(r);
		return new ResponseSuccess(200, "", new ResponseRecruitmentId(r.getId()));
	}
	
	@PatchMapping("/state/{recruitId}")
	public ResponseSuccess state(@PathVariable("recruitId") Long recruitId) {
		recruitmentService.state(recruitId);
		Recruitment r = recruitmentRepository.findOne(recruitId);
		String msg = r.getState() == State.OPEN ? "오픈" : "마감";
		return new ResponseSuccess(200, "성공적으로 " + msg + "했습니다.", new ResponseRecruitmentId(r.getId()));
	}
	
	@Data
	@AllArgsConstructor
	static class ResponseSearchRecruitment {
		List<RecruitmentDto> recruitments;
	}
	
	@Data
	@AllArgsConstructor
	static class ResponseRecruitmentId {
		Long id;
	}
	
	@Data
	static class RecruitmentDto {
		private Long id;
		
		private Category category;
		
		private String member;
		
		@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime dueDate;
		
		private String[] skill;
		
		private Personnel personnel;
		
		private Required required;
		
		private String selected;
		
		public RecruitmentDto(Recruitment recruitment) {
			this.id = recruitment.getId();
			this.category = recruitment.getCategory();
			this.member = recruitment.getMember().getName();
			this.dueDate = recruitment.getDueDate();
			this.skill = recruitment.getSkill().stream().toArray(String[]::new);
			this.personnel = recruitment.getPersonnel();
			this.required = recruitment.getRequired();
			this.selected = recruitment.getSelected();
		}
	}

	@Data
	@AllArgsConstructor
	static class ResponseCreateRecruitment {
		private Long id;
	}
	
	@Data
	static class RequestRecruitmentDto {
		
		private Category category;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime dueDate;
		
		private String[] skill;
		
		private PersonnelDto personnel;
		
		private String purpose;
		
		private String time;
		
		private String contact;
		
		private String process;
		
		private String selected;
		
	}
	
	@Data
	static class PersonnelDto {
		private int back;
		private int front;
		private int pm;
		private int other;
		private int design;
	}
	
}
