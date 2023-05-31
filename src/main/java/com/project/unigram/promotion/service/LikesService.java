package com.project.unigram.promotion.service;

import com.project.unigram.promotion.dto.LikesRequestDto;
import com.project.unigram.promotion.dto.PromotionDetailDto;


public interface LikesService {
    PromotionDetailDto likeUpdate(Long promotionId);
}
