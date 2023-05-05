package com.project.unigram.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.Personnel;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.Required;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ResponseDetailRecruitmentDto {
	
	private Long id;
	
	private Category category;
	
	private String title;
	
	private String memberName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime deadline;
	
	private String[] tech;
	
	private Personnel personnel;
	
	private Required required;
	
	private String selected;
	
	private boolean owner;
	
	public ResponseDetailRecruitmentDto(Recruitment recruitment, boolean owner) {
		this.id = recruitment.getId();
		this.category = recruitment.getCategory();
		this.title = recruitment.getTitle();
		this.memberName = recruitment.getMember().getName();
		this.createdAt = recruitment.getCreatedAt();
		this.deadline = recruitment.getDeadline();
		this.tech = recruitment.getTech().stream().toArray(String[]::new);
		this.personnel = recruitment.getPersonnel();
		this.required = recruitment.getRequired();
		this.selected = recruitment.getSelected();
		this.owner = owner;
	}
	
}
