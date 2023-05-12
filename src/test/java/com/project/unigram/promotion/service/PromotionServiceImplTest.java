package com.project.unigram.promotion.service;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.repository.PromotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PromotionServiceImplTest {

    @Autowired
    private PromotionRepository promotionRepository;

    MemberRepository memberRepository = new MemberRepository();


    @DisplayName("Posts register test")
    @Test
    void savePromotion() {
        //given
        String title = "test title1";
        String content = "test content1";
        String abstractContent = "test abstractContent1";

        PromotionCreateDto promotionCreateDto = new PromotionCreateDto();
        promotionCreateDto.setTitle(title);
        promotionCreateDto.setContent(content);
        promotionCreateDto.setAbstractContent(abstractContent);


        PromotionDto promotionDto1 = new PromotionDto();

    }

    @Test
    void getPromotion() {
    }

    @Test
    void write() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}