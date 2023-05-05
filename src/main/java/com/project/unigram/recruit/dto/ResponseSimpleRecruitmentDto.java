package com.project.unigram.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.State;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ResponseSimpleRecruitmentDto {
	
	private Long id; // 글 아이디
	private String memberName; // 사용자 이름
	private Category category; // 카테고리
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt; // 작성 시각
	
	private String title; // 제목
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime deadline; // 마감일
	private State state; // 마감 여부
	private String[] tech; // 기술
	
	public ResponseSimpleRecruitmentDto(Recruitment recruitment) {
		this.id = recruitment.getId();
		this.category = recruitment.getCategory();
		this.memberName = recruitment.getMember().getName();
		this.createdAt = recruitment.getCreatedAt();
		this.title = recruitment.getTitle();
		this.deadline = recruitment.getDeadline();
		this.state = recruitment.getState();
		this.tech = recruitment.getTech().stream().toArray(String[]::new);
	}
	
}
