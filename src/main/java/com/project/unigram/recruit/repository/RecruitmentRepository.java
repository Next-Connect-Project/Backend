package com.project.unigram.recruit.repository;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.recruit.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>, RecruitmentQueryRepository {
	
	Optional<Recruitment> findById(Long id);
	List<Recruitment> findByMemberOrderByCreatedAtAsc(Member member);
	
}
