package com.project.unigram.recruit.dto;

import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.State;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecruitmentSearchDto {
	
	private Category category;
	private State state;
	private int offset;
	private int limit;
	
}
