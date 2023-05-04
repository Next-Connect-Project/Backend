package com.project.unigram.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.unigram.recruit.domain.Recruitment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@Column(name = "member_id")
	private Long id; // 네이버에서 발급받는 고유 아이디
	
	private String name;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Recruitment> recruitments = new ArrayList<>();
	
	@Builder // setter를 사용하지 않고 Builder 사용
	public Member(Long id, String name, String email, Role role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}
	
}
