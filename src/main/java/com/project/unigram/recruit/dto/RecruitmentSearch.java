package com.project.unigram.recruit.dto;

import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.State;
import lombok.Data;

@Data
public class RecruitmentSearch {
	
	private Category category;
	private State state;
	private int offset;
	private int limit;
	
	public RecruitmentSearch(Category category, State state, int page, int limit) {
		this.category = category;
		this.state = state;
		// page 1부터 시작
		this.offset = (page - 1) * limit;
		this.limit = limit;
	}
	
}
