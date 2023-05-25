package com.project.unigram.promotion.repository;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.unigram.promotion.dto.PromotionDto;
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

        Promotion promotion = new Promotion(member, promotionCreateDto.getTitle(), promotionCreateDto.getContent(), promotionCreateDto.getAbstractContent());

        //when
        PromotionDto promotionDto1 = promotionService.write(promotionCreateDto, member);

        //then
        assertThat(member.getName()).isEqualTo(promotionDto1.getName());
        assertThat(promotionCreateDto.getTitle()).isEqualTo(promotionDto1.getTitle());
        assertThat(promotionCreateDto.getContent()).isEqualTo(promotionDto1.getContent());
        assertThat(promotionCreateDto.getAbstractContent()).isEqualTo(promotionDto1.getAbstractContent());

    }

    @DisplayName("id값과_제목으로_상품확인")
    @Test
    void findPromotionById(){
        //given
        Member member = new Member(1L, "testname1", "emailtest1", Role.NAVER);
        Promotion promotion1 = Promotion.promotionBuilder()
                .postId(1L)
                .title("title1")
                .content("content1")
                .abstractContent("abstractContent1")
                .build();
        Promotion promotion2 = Promotion.promotionBuilder()
                .postId(2L)
                .title("title2")
                .content("content2")
                .abstractContent("abstractContent2")
                .build();
        Promotion promotion3 = Promotion.promotionBuilder()
                .postId(3L)
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
                        Tuple.tuple(1L, "title1"),
                        Tuple.tuple(2L, "title2"),
                        Tuple.tuple(3L, "title3")
                );
    }
}