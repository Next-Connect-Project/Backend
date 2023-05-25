package com.project.unigram.recruit.domain;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.global.dto.Period;
import com.project.unigram.global.exception.ServerErrorCode;
import com.project.unigram.global.exception.ServerException;
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
	
	private String title;
	
	private LocalDateTime deadline;
	
	@ElementCollection
	@CollectionTable(name = "tech", joinColumns =
		@JoinColumn(name = "recruitment_id")
	)
	@Column(name = "tech_name")
	private List<String> tech;
	
	@Embedded
	private Personnel personnel;
	
	@Embedded
	private Required required;
	
	private String free;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	public static Recruitment create(Member member,
	                          Category category,
							  String title,
	                          LocalDateTime deadline,
	                          String[] tech,
	                          Personnel personnel,
	                          Required required,
	                          String free) {
		Recruitment recruitment = new Recruitment();
		
		recruitment.setMember(member);
		recruitment.category = category;
		recruitment.title = title;
		recruitment.deadline = deadline;
		recruitment.tech = Arrays.stream(tech).collect(Collectors.toList());
		recruitment.personnel = personnel;
		recruitment.required = required;
		recruitment.free = free;
		recruitment.state = State.OPEN;
		
		return recruitment;
	}
	
	public void updateAll(Category category,
						  String title,
	                      LocalDateTime deadline,
	                      List<String> tech,
	                      Personnel personnel,
	                      Required required,
	                      String free) {
		this.category = category;
		this.title = title;
		this.deadline = deadline;
		this.tech = tech;
		this.personnel = personnel;
		this.required = required;
		this.free = free;
	}
	
	public void close() {
		if (this.state == State.CLOSE) throw new ServerException("이미 마감된 글입니다.", ServerErrorCode.INTERNAL_SERVER);
		this.state = State.CLOSE;
	}
	
	public void open() {
		if (this.state == State.OPEN) throw new ServerException("이미 오픈된 글입니다.", ServerErrorCode.INTERNAL_SERVER);
		this.state = State.OPEN;
	}
	
	public boolean isAuthorizedMember(Long memberId) {
		if (member.getId() != memberId) return false;
		return true;
	}
	
	private void setMember(Member member) {
		this.member = member;
		member.getRecruitments().add(this);
	}
	
}
