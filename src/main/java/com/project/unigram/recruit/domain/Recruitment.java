package com.project.unigram.recruit.domain;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.global.dto.Period;
import com.project.unigram.recruit.exception.RecruitException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "recruitment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends Period {

	@Id @GeneratedValue
	@Column(name = "recruitment_id")
	private Long id;
	
	// join
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id") // 연관 관계 주인
	private Member member;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	private LocalDateTime dueDate;
	
	@ElementCollection
	@CollectionTable(name = "skill", joinColumns =
		@JoinColumn(name = "recruitment_id")
	)
	@Column(name = "skill_name")
	private List<String> skill;
	
	@Embedded
	private Personnel personnel;
	
	@Embedded
	private Required required;
	
	private String selected;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	public static Recruitment create(Member member,
	                          Category category,
	                          LocalDateTime dueDate,
	                          String[] skill,
	                          Personnel personnel,
	                          Required required,
	                          String selected) {
		Recruitment recruitment = new Recruitment();
		
		recruitment.setMember(member);
		recruitment.category = category;
		recruitment.dueDate = dueDate;
		recruitment.skill = Arrays.stream(skill).collect(Collectors.toList());
		recruitment.personnel = personnel;
		recruitment.required = required;
		recruitment.selected = selected;
		recruitment.state = State.OPEN;
		
		return recruitment;
	}
	
	public void close() {
		if (this.state == State.CLOSE) throw new RecruitException("이미 마감된 글입니다.");
		this.state = State.CLOSE;
	}
	
	private void setMember(Member member) {
		this.member = member;
		member.getRecruitments().add(this);
	}
	
}
