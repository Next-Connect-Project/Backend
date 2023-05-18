package com.project.unigram.recruit.repository;

import com.project.unigram.auth.domain.QMember;
import com.project.unigram.recruit.domain.*;
import com.project.unigram.recruit.dto.RecruitmentSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>, RecruitmentQueryRepository {
	
	Optional<Recruitment> findById(Long id);
	
}
