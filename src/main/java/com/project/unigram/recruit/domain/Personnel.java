package com.project.unigram.recruit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Personnel {
	
	@NotNull(message = "NOT_EMPTY:backNumber가 null이어선 안됩니다.")
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:backNumber 범위를 벗어났습니다. 0 - 100")
	private Integer backNumber;
	@NotNull(message = "NOT_EMPTY:frontNumber가 null이어선 안됩니다.")
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:frontNumber 범위를 벗어났습니다. 0 - 100")
	private Integer frontNumber;
	@NotNull(message = "NOT_EMPTY:designNumber가 null이어선 안됩니다.")
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:designNumber 범위를 벗어났습니다. 0 - 100")
	private Integer designNumber;
	@NotNull(message = "NOT_EMPTY:pmNumber가 null이어선 안됩니다.")
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:PMNumber 범위를 벗어났습니다. 0 - 100")
	private Integer pmNumber;
	@NotNull(message = "NOT_EMPTY:otherNumber가 null이어선 안됩니다.")
	@Range(min = 0, max = 100, message = "OUT_OF_RANGE:otherNumber 범위를 벗어났습니다. 0 - 100")
	private Integer otherNumber;
	
}

