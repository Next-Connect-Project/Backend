package com.project.unigram.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.Personnel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRecruitmentDto {
	
	private Category category;
	
	private String title;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime deadline;
	
	private String[] tech;
	
	private Personnel personnel;
	
	private String purpose;
	
	private String progress;
	
	private String contact;
	
	private String duration;
	
	private String timeandplace;
	
	private String way;
	
	private String selected;

}
