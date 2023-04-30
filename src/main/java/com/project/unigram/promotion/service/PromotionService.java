package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository ;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository){
        this.promotionRepository=promotionRepository;
    }

    //전체 게시물 조회
    @Transactional(readOnly = true)
    public List<PromotionDto> getPromotions(){
        List<Promotion> promotions=promotionRepository.findAll();
        List<PromotionDto> promotionDtos=new ArrayList<>();
        promotions.forEach(s->promotionDtos.add(PromotionDto.toDto(s)));

//        //최신순 정렬
//        Comparator<Promotion> comparingDateReverse=Comparator.comparing(Promotion::getCreatedAt, Comparator.reverseOrder());
//        List<Promotion> promotionList=promotions.stream()
//                        .sorted(comparingDateReverse).collect(Collectors.toList());
//        promotionList.forEach(s->promotionDtos.add(PromotionDto.toDto(s)));
        return promotionDtos;
    }

    //개별 게시물 조회
    @Transactional(readOnly = true)
    public PromotionDto getPromotion(Long id){
        Promotion promotion=promotionRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Promotion Id를 찾을 수 없습니다.");
        });
        PromotionDto promotionDto=PromotionDto.toDto(promotion);
        return promotionDto;
    }

    //게시물 작성
    @Transactional
    public PromotionDto write(PromotionCreateDto promotionCreateDto, Member member){
        Promotion promotion=new Promotion(member, promotionCreateDto.getTitle(), promotionCreateDto.getContent(), promotionCreateDto.getAbstractContent());

        promotionRepository.save(promotion);
        return PromotionDto.toDto(promotion);

    }




}
