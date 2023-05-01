package com.project.unigram.recruit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Personnel {
	
	private Integer backNumber;
	private Integer frontNumber;
	private Integer designNumber;
	private Integer PMNumber;
	private Integer otherNumber;
	
}

