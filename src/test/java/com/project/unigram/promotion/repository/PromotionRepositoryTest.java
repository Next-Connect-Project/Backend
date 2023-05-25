package com.project.unigram.promotion.repository;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.unigram.promotion.dto.PromotionDetailDto;
import com.project.unigram.promotion.dto.PromotionTotalDto;
import com.project.unigram.promotion.service.PromotionService;
import com.project.unigram.promotion.service.PromotionServiceImpl;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional

public class PromotionRepositoryTest {

    @Autowired
    PromotionServiceImpl promotionService;

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LikesRepository likesRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @DisplayName("게시글 저장 테스트")
    @Test
    public void 게시글_저장_테스트_service코드_이용(){

        //given
        final PromotionCreateDto promotionCreateDto = new PromotionCreateDto();
        promotionCreateDto.setTitle("promotion title 1");
        promotionCreateDto.setContent("promotion content1");
        promotionCreateDto.setAbstractContent("abstract content1");
        Member member = new Member(1L, "song", "ghenrhkwk88@naver.com", Role.NAVER);
        memberRepository.save(member);
        Promotion promotion = new Promotion(member, promotionCreateDto.getTitle(), promotionCreateDto.getContent(), promotionCreateDto.getAbstractContent());
        promotionService = new PromotionServiceImpl(promotionRepository, memberService, likesRepository);

        //when
        PromotionTotalDto promotionTotalDto1 = promotionService.write(promotionCreateDto, member);

        //then
        assertThat(member.getName()).isEqualTo(promotionTotalDto1.getName());
        assertThat(promotionCreateDto.getTitle()).isEqualTo(promotionTotalDto1.getTitle());
        assertThat(promotionCreateDto.getContent()).isEqualTo(promotionTotalDto1.getContent());
        assertThat(promotionCreateDto.getAbstractContent()).isEqualTo(promotionTotalDto1.getAbstractContent());

    }

    @DisplayName("id값과_제목으로_게시물_일치확인")
    @Test
    void findPromotionById(){
        //given
        Member member = new Member(1L, "testname1", "emailtest1", Role.NAVER);
        Promotion promotion1 = Promotion.promotionBuilder()
                .title("title1")
                .content("content1")
                .abstractContent("abstractContent1")
                .build();
        Promotion promotion2 = Promotion.promotionBuilder()
                .title("title2")
                .content("content2")
                .abstractContent("abstractContent2")
                .build();
        Promotion promotion3 = Promotion.promotionBuilder()
                .title("title3")
                .content("content3")
                .abstractContent("abstractContent3")
                .build();

        promotionRepository.saveAll(List.of(promotion1, promotion2, promotion3));

        //when
        List<Promotion> promotions = promotionRepository.findAll();

        //then
        Assertions.assertThat(promotions).hasSize(3)
                .extracting("promotionId","title")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(promotion1.getPromotionId(), "title1"),
                        Tuple.tuple(promotion2.getPromotionId(), "title2"),
                        Tuple.tuple(promotion3.getPromotionId(), "title3")
                );
    }
}