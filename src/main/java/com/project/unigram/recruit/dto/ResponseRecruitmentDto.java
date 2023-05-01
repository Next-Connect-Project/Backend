package com.project.unigram.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.Personnel;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.Required;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseRecruitmentDto {
	
	private Long id;
	
	private Category category;
	
	private String title;
	
	private String member;
	
	@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime deadline;
	
	private String[] tech;
	
	private Personnel personnel;
	
	private Required required;
	
	private String selected;
	
	public ResponseRecruitmentDto(Recruitment recruitment) {
		this.id = recruitment.getId();
		this.category = recruitment.getCategory();
		this.member = recruitment.getMember().getName();
		this.deadline = recruitment.getDeadline();
		this.tech = recruitment.getTech().stream().toArray(String[]::new);
		this.personnel = recruitment.getPersonnel();
		this.required = recruitment.getRequired();
		this.selected = recruitment.getSelected();
	}
	
}
