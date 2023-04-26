package com.project.unigram.recruit.repository;

import com.project.unigram.recruit.domain.Recruitment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RecruitmentRepository {
	
	@PersistenceContext
	EntityManager em;
	
	public void save(Recruitment recruitment) {
		em.persist(recruitment);
	}
	
}
