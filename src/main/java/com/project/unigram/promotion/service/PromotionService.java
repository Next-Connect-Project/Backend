package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;

import java.util.List;


public interface PromotionService {
    List<PromotionDto> getPromotions(int page, int limit);
    PromotionDto getPromotion(Long id);
    PromotionDto write(PromotionCreateDto promotionCreateDto, Member member);
    PromotionDto update(Long id, PromotionDto promotionDto);
    void delete(Long id);
    List<PromotionDto> getFourPromotions();
}
