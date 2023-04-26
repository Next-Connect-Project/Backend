package com.project.unigram.recruit.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Required {
	
	private String purpose;
	private String time;
	private String contact;
	private String process;
	
	public Required(String purpose, String time, String contact, String process) {
		this.purpose = purpose;
		this.time = time;
		this.contact = contact;
		this.process = process;
	}
}
