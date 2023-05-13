package com.project.unigram.recruit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Personnel {
	
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:backNumber 범위를 벗어났습니다. 0 - 10")
	private Integer backNumber;
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:frontNumber 범위를 벗어났습니다. 0 - 10")
	private Integer frontNumber;
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:designNumber 범위를 벗어났습니다. 0 - 10")
	private Integer designNumber;
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:PMNumber 범위를 벗어났습니다. 0 - 10")
	private Integer pmNumber;
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:otherNumber 범위를 벗어났습니다. 0 - 10")
	private Integer otherNumber;
	
}

