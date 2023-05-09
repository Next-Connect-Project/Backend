package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.repository.PromotionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.project.unigram.auth.domain.QMember.member;
import static org.junit.jupiter.api.Assertions.*;

class PromotionServiceImplTest {

    @Autowired
    private PromotionRepository promotionRepository;

    @BeforeEach
    public void clean(){
        promotionRepository.deleteAll();
    }

    @DisplayName("Posts register test")
    @Test
    void savePromotion() {
        //given
        String title = "test title1";
        String content = "test content1";
        String abstractContent = "test abstractContent1";

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