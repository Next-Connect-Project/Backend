package com.project.unigram.promotion.repository;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionMoreOverviewDto;
import com.project.unigram.promotion.dto.PromotionOverviewDto;
import com.project.unigram.promotion.service.PromotionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@SpringBootTest
@Transactional
public class PromotionServiceImplTest {
    @Autowired
    PromotionService promotionService;
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    void 게시물_리스트_확인_테스트() {
        //given
        Member member = new Member(1L, "member1", "memberEmail@naver.com", Role.NAVER);
        memberRepository.save(member);
        Promotion promotion = new Promotion(member, "promotion title1", "promotion content1", "promotion ac1");
        PromotionCreateDto promotionCreateDto = new PromotionCreateDto();

        //when
        promotionService.write(promotionCreateDto.toDto(promotion), member);


        //then
        PromotionMoreOverviewDto promotions = promotionService.getPromotions(1, 16, 0);
        List<PromotionOverviewDto> promotionOverviewDtos = promotions.getPromotionOverviewDtoList();
        for (PromotionOverviewDto promotionOverviewDto : promotionOverviewDtos) {

        }
    }
}
