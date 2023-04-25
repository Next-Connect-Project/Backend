package com.project.unigram.promotion.service;

import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository ;

    //전체 게시물 조회
    @Transactional(readOnly = true)
    public List<PromotionDto> getPromotions(){
        List<Promotion> promotions=promotionRepository.findAll();
        List<PromotionDto> promotionDtos=new ArrayList<>();
        promotions.forEach(s->promotionDtos.add(PromotionDto.toDto(s)));
        return promotionDtos;
    }

    //개별 게시물 조회
    @Transactional(readOnly = true)
    public PromotionDto getPromotion(Long id){
        Promotion promotion=promotionRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다.");
        });
        PromotionDto promotionDto=PromotionDto.toDto(promotion);
        return promotionDto;
    }

    //게시물 작성

}
