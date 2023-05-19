package com.project.unigram.recruit.repository;

import com.project.unigram.recruit.domain.Recruitment;
import com.project.unigram.recruit.dto.RecruitmentSearch;


import java.util.List;

public interface RecruitmentQueryRepository {
	
	List<Recruitment> findRecruitmentWithSearch(RecruitmentSearch recruitmentSearch);
	Long countRecruitmentWithSearch(RecruitmentSearch recruitmentSearch);
	List<Recruitment> findFastDeadlineRecruiment();

}
