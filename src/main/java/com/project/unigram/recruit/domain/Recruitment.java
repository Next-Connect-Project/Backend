package com.project.unigram.recruit.domain;

import com.project.unigram.auth.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment {

	@Id @GeneratedValue
	@Column(name = "recruitment_id")
	private Long id;
	
	// join
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	private Category category;
	
	@Embedded
	private Period period;
	
	private LocalDateTime dueDate;
	
	@ElementCollection
	@CollectionTable(name = "skill_table", joinColumns =
		@JoinColumn(name = "recruitment_id")
	)
	@Column(name = "skill_name")
	private List<String> skill;
	
	@Embedded
	private Personnel personnel;
	
	@Embedded
	private Detail detail;
	
	private String select;
	
	private State state;
	
}
