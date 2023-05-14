package com.project.unigram.promotion.repository;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class PromotionRepositoryTest {
    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    MemberRepository memberRepository;

    private PromotionCreateDto promotionCreateDto = new PromotionCreateDto();
    private Promotion promotion;
    private Member member;

    @DisplayName("게시글 저장 테스트")
    @Rollback(false)
    @Test
    public void 게시글_저장_테스트(){
        //given
//        promotionCreateDto.setTitle("promotion title 1");
//        promotionCreateDto.setContent("promotion content1");
//        promotionCreateDto.setAbstractContent("abstract content1");
//        Member member = new Member(1L, "song", "ghenrhkwk88@naver.com", Role.NAVER);
//        memberRepository.save(member);
//
//        promotion = new Promotion(member, promotion.getTitle(), promotion.getContent(), promotion.getAbstractContent());
//
//        //when
//        Promotion promotion1 = promotionRepository.save(promotion);
//
//        //then
//        assertThat(promotion1).isEqualTo(promotion);
//        assertThat(promotion1.getTitle()).isEqualTo(promotion.getTitle());
//        assertThat(promotion1.getContent()).isEqualTo(promotion.getContent());
//        assertThat(promotion1.getAbstractContent()).isEqualTo(promotion.getAbstractContent());
//        assertThat(promotion1.getMember()).isEqualTo(promotion.getMember());
//        assertThat(promotion1.getPostId()).isEqualTo(promotion.getPostId());
    }

}