package com.project.unigram.recruit.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Required {
	
	private String purpose;
	private String progress;
	private String contact;
	private String duration;
	private String timeandplace;
	private String way;
	
	@Builder
	public Required(String purpose, String progress, String contact, String duration, String timeandplace, String way) {
		this.purpose = purpose;
		this.progress = progress;
		this.contact = contact;
		this.duration = duration;
		this.timeandplace = timeandplace;
		this.way = way;
	}
	
}
