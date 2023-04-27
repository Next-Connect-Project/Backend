package com.project.unigram.recruit.repository;

import com.project.unigram.auth.domain.QMember;
import com.project.unigram.recruit.domain.*;
import com.project.unigram.recruit.dto.RecruitmentSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RecruitmentRepository {
	
	@PersistenceContext
	EntityManager em;
	
	public void save(Recruitment recruitment) {
		em.persist(recruitment);
	}
	
	public List<Recruitment> findRecruitmentWithSearch(RecruitmentSearchDto recruitmentSearchDto) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		QRecruitment recruitment = QRecruitment.recruitment;
		QMember member = QMember.member;
		
		return query.selectFrom(recruitment)
			       .join(recruitment.member, member)
			       .where(categoryEq(recruitmentSearchDto.getCategory()), stateEq(recruitmentSearchDto.getState()))
			       .offset(recruitmentSearchDto.getOffset())
			       .limit(recruitmentSearchDto.getLimit())
			       .fetch();
	}
	
	private BooleanExpression categoryEq(Category category) {
		if (category == null) return null;
		return QRecruitment.recruitment.category.eq(category);
	}
	
	private BooleanExpression stateEq(State state) {
		if (state == null) return null;
		return QRecruitment.recruitment.state.eq(state);
	}
	
}
