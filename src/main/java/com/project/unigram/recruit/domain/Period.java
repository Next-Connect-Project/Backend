package com.project.unigram.recruit.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {
	
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	
}
