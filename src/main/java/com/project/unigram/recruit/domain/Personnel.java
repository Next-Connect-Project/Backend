package com.project.unigram.recruit.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Personnel {
	
	private Integer back;
	private Integer front;
	private Integer design;
	private Integer pm;
	private Integer other;
	
	public Personnel(Integer back, Integer front, Integer design, Integer pm, Integer other) {
		this.back = back;
		this.front = front;
		this.design = design;
		this.pm = pm;
		this.other = other;
	}
	
}

