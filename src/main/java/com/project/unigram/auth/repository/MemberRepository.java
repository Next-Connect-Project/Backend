package com.project.unigram.auth.repository;

import com.project.unigram.auth.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
	
	@PersistenceContext
	EntityManager em;
	
	public Long save(Member member) {
		em.persist(member);
		
		return member.getId();
	}
	
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
}
