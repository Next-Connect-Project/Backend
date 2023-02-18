package com.project.unigram.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@Column(name = "member_id")
	private Long id; // 네이버에서 발급받는 고유 아이디
	
	private String name;
	
	private String email;
	
	private Role role;
	
}
