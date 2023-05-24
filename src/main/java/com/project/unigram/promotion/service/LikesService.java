package com.project.unigram.promotion.service;

import com.project.unigram.promotion.dto.LikesRequestDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.repository.LikesRepository;
import org.springframework.stereotype.Service;


public interface LikesService {
    PromotionDto likeUpdate(LikesRequestDto likesRequestDto);
}
