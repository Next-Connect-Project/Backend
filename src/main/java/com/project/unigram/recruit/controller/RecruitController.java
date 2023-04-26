package com.project.unigram.recruit.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.global.dto.ResponseSuccess;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.Personnel;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.Required;
import com.project.unigram.recruit.repository.RecruitmentRepository;
import com.project.unigram.recruit.service.RecruitmentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/recruit")
@RequiredArgsConstructor
public class RecruitController {
	
	private final RecruitmentService recruitmentService;
	private final MemberService memberService;

	@PostMapping("/create")
	public ResponseSuccess create(@RequestBody @Valid RecruitmentDto recruitmentDto) {
		Member member = memberService.getMember();
		
		PersonnelDto personnelDto = recruitmentDto.getPersonnel();
		Personnel personnel = new Personnel(personnelDto.getBack(), personnelDto.getFront(), personnelDto.getDesign(), personnelDto.getPm(), personnelDto.getOther());
		
		Required required = new Required(recruitmentDto.getPurpose(), recruitmentDto.getTime(), recruitmentDto.getContact(), recruitmentDto.getProcess());
		
		Recruitment recruitment = Recruitment.create(member,
				recruitmentDto.getCategory(),
				recruitmentDto.getDueDate(),
				recruitmentDto.getSkill(),
				personnel,
				required,
				recruitmentDto.getSelected());
		
		Long id = recruitmentService.post(recruitment);
		
		return new ResponseSuccess(200, "저장에 성공했습니다.", new ResponseCreateRecruitment(id));
	}

	@Data
	@AllArgsConstructor
	static class ResponseCreateRecruitment {
		private Long id;
	}
	
	@Data
	static class RecruitmentDto {
		
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
		private int design;
		private int pm;
		private int other;
		
	}
	
}
