package com.project.unigram.promotion.dto;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.repository.PromotionRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Data
@Getter @RequiredArgsConstructor
public class PromotionMoreOverviewDto {

    private int count;
    private List<PromotionOverviewDto> promotionOverviewDtoList;

    private boolean owner;

    public PromotionMoreOverviewDto(List<PromotionOverviewDto> promotionOverviewDtoList, int count){
        this.count = count;
        this.promotionOverviewDtoList = promotionOverviewDtoList;
    }
}
