package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Likes;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.LikesRequestDto;
import com.project.unigram.promotion.dto.PromotionDetailDto;
import com.project.unigram.promotion.exception.CommonErrorCode;
import com.project.unigram.promotion.exception.PromotionException;
import com.project.unigram.promotion.repository.LikesRepository;
import com.project.unigram.promotion.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final MemberService memberService;
    private final PromotionRepository promotionRepository;
    private final PromotionService promotionService;

    @Transactional
    @Override
    public PromotionDetailDto likeUpdate(Long promotionId) {
        Member member = memberService.getMember();

        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionException("일치하는 홍보글 id값이 없습니다.", CommonErrorCode.PostId_Is_Not_Valid));

        if (member == null) {
            throw new PromotionException("일치하는 사용자 id값이 없습니다", CommonErrorCode.No_Member_Id);
        }

        Likes likes = likesRepository.findByPromotion_PromotionId(promotion.getPromotionId());

        boolean owner = false;
        if(member.getId() == promotion.getMember().getId()){
            owner = true;
            if(likes.isLikeCheck()==false || likes == null){
                //좋아요를 누른적이 없는 사용자인 경우
                likes.liked(promotion);
                promotion.setLikeCount(promotion.getLikeCount()+1);
            }else if(likes.isLikeCheck()==true){
                likes.unLiked(promotion);
                promotion.setLikeCount(promotion.getLikeCount()-1);
            }

        }else{
            owner = false;
        }
        return PromotionDetailDto.toDto(promotion, likesRepository, owner);

    }

}


