package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.dto.*;

import java.util.List;


public interface PromotionService {
    PromotionMoreOverviewDto getPromotions(int page, int limit, int sorting);
    PromotionDetailDto getPromotion(Long id);
    PromotionMoreOverviewDto getUserPromotions();
    PromotionTotalDto write(PromotionCreateDto promotionCreateDto, Member member);
    PromotionOverviewDto update(Long id, PromotionCreateDto promotionCreateDto);
    void delete(Long id);
    List<PromotionOverviewDto> getFourPromotions();
}
