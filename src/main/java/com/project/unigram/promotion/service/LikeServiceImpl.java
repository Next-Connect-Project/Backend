package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.repository.MemberRepository;
import com.project.unigram.promotion.domain.Likes;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.LikesRequestDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.exception.CommonErrorCode;
import com.project.unigram.promotion.exception.PromotionException;
import com.project.unigram.promotion.repository.LikesRepository;
import com.project.unigram.promotion.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PromotionRepository promotionRepository;

    @Transactional
    @Override
    public void likeUpdate(LikesRequestDto likesRequestDto) {
        Member member = memberRepository.findOne(likesRequestDto.getMemberId());
        if (member == null) {
            throw new PromotionException("일치하는 사용자 id값이 없습니다", CommonErrorCode.No_Member_Id);
        }

        Promotion promotion = promotionRepository.findById(likesRequestDto.getPromotionId())
                .orElseThrow(() -> new PromotionException("일치하는 홍보글 id값이 없습니다.", CommonErrorCode.PostId_Is_Not_Valid));

        Likes likes = likesRepository.findByMemberAndPromotion(member, promotion);

        if(likes == null){
            //좋아요를 누른적이 없는 사용자인 경우
            promotion.setLikeCount(promotion.getLikeCount()+1);
            Likes like = new Likes(member, promotion);
            likesRepository.save(like);
        }else if(likes.isLikeCheck()==true){
            promotion.setLikeCount(promotion.getLikeCount()-1);
            likes.setLikeCheck(false);
        }else if(likes.isLikeCheck() ==false){
            promotion.setLikeCount(promotion.getLikeCount()+1);
            likes.setLikeCheck(true);
        }

    }

}


