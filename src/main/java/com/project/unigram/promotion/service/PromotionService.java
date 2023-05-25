package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDetailDto;
import com.project.unigram.promotion.dto.PromotionOverviewDto;
import com.project.unigram.promotion.dto.PromotionTotalDto;

import java.util.List;


public interface PromotionService {
    List<PromotionOverviewDto> getPromotions(int page, int limit);
    PromotionDetailDto getPromotion(Long id);
    PromotionTotalDto write(PromotionCreateDto promotionCreateDto, Member member);
    PromotionOverviewDto update(Long id, PromotionCreateDto promotionCreateDto);
    void delete(Long id);
    List<PromotionOverviewDto> getFourPromotions();
}
