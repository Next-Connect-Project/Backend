package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.repository.LikesRepository;
import com.project.unigram.promotion.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@Transactional
public class LocalDateTimeTest {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LikesRepository likesRepository;

    @Test
    void nowDataTime(){
        LocalDateTime now = LocalDateTime.now();
        String endDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String startDateTime = now.minusWeeks(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Member member = new Member(1L, "name1" , "naver@naver.com", Role.NAVER);
        memberRepository.save(member);
        Promotion promotion = new Promotion(1L, "title1", "content1", "abstract1");
        PromotionCreateDto promotionCreateDto = new PromotionCreateDto();
        promotionCreateDto.toDto(promotion);
//        System.out.println(promotionService.write(promotionCreateDto, member));

    }
}
