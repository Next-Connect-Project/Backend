package com.project.unigram.recruit.repository;

import com.project.unigram.auth.domain.QMember;
import com.project.unigram.recruit.domain.*;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.project.unigram.recruit.exception.RecruitErrorCode;
import com.project.unigram.recruit.exception.RecruitException;
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
	
	public Recruitment findOne(Long id) {
		Recruitment recruitment = em.find(Recruitment.class, id);
		if (recruitment == null) throw new RecruitException("존재하지 않는 모집글 입니다.", RecruitErrorCode.WRONG_ID);
		return recruitment;
	}
	
	public void deleteOne(Recruitment r) {
		em.remove(r);
	}
	
	public List<Recruitment> findRecruitmentWithSearch(RecruitmentSearch recruitmentSearch) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		QRecruitment recruitment = QRecruitment.recruitment;
		QMember member = QMember.member;
		
		return query.selectFrom(recruitment)
			       .join(recruitment.member, member)
			       .where(categoryEq(recruitmentSearch.getCategory()), stateEq(recruitmentSearch.getState()))
			       .offset(recruitmentSearch.getOffset())
			       .limit(recruitmentSearch.getLimit())
			       .fetch();
	}
	
	public Long countRecruitment() {
		JPAQueryFactory query = new JPAQueryFactory(em);
		QRecruitment recruitment = QRecruitment.recruitment;
		
		return query.select(recruitment.count())
			       .from(recruitment)
			       .fetchOne();
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
