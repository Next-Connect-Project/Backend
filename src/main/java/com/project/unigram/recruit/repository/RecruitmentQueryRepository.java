package com.project.unigram.recruit.repository;

import com.project.unigram.auth.domain.QMember;
import com.project.unigram.recruit.domain.Category;
import com.project.unigram.recruit.domain.QRecruitment;
import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.domain.State;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public interface RecruitmentQueryRepository {
	List<Recruitment> findRecruitmentWithSearch(RecruitmentSearch recruitmentSearch);
	Long countRecruitmentWithSearch(RecruitmentSearch recruitmentSearch);

}
