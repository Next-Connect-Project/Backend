package com.project.unigram.recruit.repository;

import com.project.unigram.auth.domain.QMember;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.QRecruitment;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.State;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RecruitmentQueryRepositoryImpl implements RecruitmentQueryRepository {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
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
	
	@Override
	public Long countRecruitmentWithSearch(RecruitmentSearch recruitmentSearch) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		QRecruitment recruitment = QRecruitment.recruitment;
		
		return query.select(recruitment.count())
				.from(recruitment)
				.where(categoryEq(recruitmentSearch.getCategory()), stateEq(recruitmentSearch.getState()))
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
