package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.domain.Role;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.LikesRequestDto;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.exception.CommonErrorCode;
import com.project.unigram.promotion.exception.PromotionException;
import com.project.unigram.promotion.repository.LikesRepository;
import com.project.unigram.promotion.repository.PromotionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.unigram.promotion.domain.QPromotion.promotion;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class LikeServiceImplTest {
    @Autowired
    private PromotionRepository promotionRepository;

   @Autowired
   private PromotionService promotionService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 추천_누른_결과_true_확인_테스트() {
        //given
        Member member = new Member(1L, "member name1", "memberemail@naver.com", Role.ADMIN);
        memberRepository.save(member);
        PromotionCreateDto promotionCreateDto = new PromotionCreateDto("promotion title1", "promotion content 1", "promotion abstractContent1");

        //when
        PromotionDto promotionDto = promotionService.write(promotionCreateDto, member);
        likesService.likeUpdate(new LikesRequestDto(member.getId(), promotionDto.getId()));
        Promotion promotion = promotionRepository.findById(promotionDto.getId())
                .orElseThrow(()->new PromotionException("존재하는 홍보 게시글이 없습니다.", CommonErrorCode.PostId_Is_Not_Valid));

        //then
        Assertions.assertThat(likesRepository.findByMemberAndPromotion(member, promotion).isLikeCheck()).isEqualTo(true);
    }

    @Test
    void 추천을_다시_누른_결과_false_확인_테스트(){
        //given
        Member member = new Member(1L, "member name1", "memberemail@naver.com", Role.ADMIN);
        memberRepository.save(member);
        PromotionCreateDto promotionCreateDto = new PromotionCreateDto("promotion title1", "promotion content 1", "promotion abstractContent1");

        //when
        PromotionDto promotionDto = promotionService.write(promotionCreateDto, member);
        likesService.likeUpdate(new LikesRequestDto(member.getId(), promotionDto.getId()));
        likesService.likeUpdate(new LikesRequestDto(member.getId(), promotionDto.getId()));
        Promotion promotion = promotionRepository.findById(promotionDto.getId())
                .orElseThrow(()->new PromotionException("존재하는 홍보 게시글이 없습니다.", CommonErrorCode.PostId_Is_Not_Valid));

        //then
        Assertions.assertThat(likesRepository.findByMemberAndPromotion(member, promotion)).isEqualTo(null);
    }

    @Test
    void 추천_누른_결과_홍보게시판_적용_확인_테스트(){
        //given
        Member member = new Member(1L, "member name1", "memberemail@naver.com", Role.ADMIN);
        Member member2 = new Member(2L, "member name2", "memberemail@naver.com", Role.ADMIN);
        memberRepository.save(member);
        memberRepository.save(member2);
        PromotionCreateDto promotionCreateDto = new PromotionCreateDto("promotion title1","promotion content1", "promotion abstractContent1");
        PromotionCreateDto promotionCreateDto2 = new PromotionCreateDto("promotion title2","promotion content2", "promotion abstractContent2");

        //when
        PromotionDto promotionDto = promotionService.write(promotionCreateDto, member);
        promotionService.write(promotionCreateDto2, member2);
        likesService.likeUpdate(new LikesRequestDto(member.getId(), promotionDto.getId()));

        //then
        List<PromotionDto> promotionDtos = promotionService.getPromotions(1,16);
        Assertions.assertThat(promotionDtos.get(0).getLikeCount()).isEqualTo(1);
        Assertions.assertThat(promotionDtos.get(1).getLikeCount()).isEqualTo(0);

    }
}