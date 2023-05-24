package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Likes;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.exception.CommonErrorCode;
import com.project.unigram.promotion.exception.PromotionException;
import com.project.unigram.promotion.repository.LikesRepository;
import com.project.unigram.promotion.repository.PromotionRepository;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final MemberService memberService;
    private final LikesRepository likesRepository;

    //전체 게시물 조회
    @Override
    @Transactional(readOnly = true)
    public List<PromotionDto> getPromotions(int page, int limit) {

        List<Promotion> promotions = promotionRepository.findAll();
        List<PromotionDto> promotionDtos = new ArrayList<>();
        List<PromotionDto> promotionList=new ArrayList<>();
        int firstPageNum=1, lastPageNum=16;
        if(promotions.size()!=0){
            promotions.forEach(s -> promotionDtos.add(PromotionDto.toDto(s, likesRepository)));

            //1-16, 17-32 인덱스를 가진 게시글을 보여야 하므로 다음과 같이 페이징 처리를 한다.
            lastPageNum=limit*page;
            firstPageNum=lastPageNum-15;
            if(promotionDtos.size()<=lastPageNum){
                lastPageNum=promotionDtos.size();
            }
            //최신순 정렬을 한다.
//            Comparator<PromotionDto> comparingDateReverse = Comparator.comparing(PromotionDto::getCreatedAt, Comparator.naturalOrder());
            promotionList = promotionDtos.stream()
                    .sorted(Comparator.comparing(PromotionDto::getCreatedAt))
                    .collect(Collectors.toList())
                    .subList(firstPageNum-1, lastPageNum);
        }

        return promotionList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PromotionDto> getFourPromotions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = now.minusWeeks(2);

        List<Promotion> promotionList = promotionRepository.findAllByCreatedAtBetween(startDateTime, now);
        if(promotionList.size() == 0){
            throw new PromotionException("게시물이 존재하지 않습니다.",CommonErrorCode.Content_Is_Not_Empty);
        }

        List<PromotionDto> promotionDtoList = new ArrayList<>();
        List<PromotionDto> finalPromotionDtos = new ArrayList<>();
        if(promotionList.size() != 0){
            promotionList.forEach(s -> promotionDtoList.add(PromotionDto.toDto(s, likesRepository)));

            finalPromotionDtos = promotionDtoList.stream()
                    .sorted(Comparator.comparing(PromotionDto::getLikeCount,Comparator.reverseOrder()))
                    .collect(Collectors.toList())
                    .subList(0, 4);
        }
        return finalPromotionDtos;
    }

    //개별 게시물 조회
    @Override
    @Transactional(readOnly = true)
    public PromotionDto getPromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> {
            throw new PromotionException("Promotion Id를 찾을 수 없습니다.", CommonErrorCode.PostId_Is_Not_Valid);
        });
        PromotionDto promotionDto = PromotionDto.toDto(promotion, likesRepository);
        return promotionDto;
    }

    //게시물 작성
    @Override
    @Transactional
    public PromotionDto write(PromotionCreateDto promotionCreateDto, Member member) {

        Promotion promotion = new Promotion(member, promotionCreateDto.getTitle(), promotionCreateDto.getContent(), promotionCreateDto.getAbstractContent());
        promotionRepository.save(promotion);
        likesRepository.save(new Likes(promotion.getMember(), promotion));
        return PromotionDto.toDto(promotion, likesRepository);

    }

    //게시글 수정
    @Override
    @Transactional
    public PromotionDto update(Long id, PromotionDto promotionDto){
        Promotion promotion=promotionRepository.findById(id).orElseThrow(()->{
            return new PromotionException("Promotion Id를 찾을 수 없습니다", CommonErrorCode.PostId_Is_Not_Valid);
        });

        Member member= memberService.getMember();

        //현재 회원과 게시글 등록 회원이 일치하는지 확인한다.
        try {
            if (promotion.getMember().getId().equals(member.getId())) {
                promotion.setTitle(promotionDto.getTitle());
                promotion.setContent(promotionDto.getContent());
                promotion.setAbstractContent(promotionDto.getAbstractContent());
                promotion.setMember(member);
            }
        }catch(NullPointerException e){

        }
        return PromotionDto.toDto(promotion, likesRepository);

    }

    //게시글 삭제
    @Override
    @Transactional
    public void delete(Long id) {
        //게시글이 존재하는지 먼저 확인하고 없다면 오류 처리를 한다.
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> {
            return new PromotionException("Promotion Id를 찾을 수 없습니다.", CommonErrorCode.PostId_Is_Not_Valid);
        });

        //게시글 아이디를 작성한 사람과 현재 로그인한 사람이 같으면 삭제처리를 한다.
        Member member = memberService.getMember();

        try{
            if(member!=null){
                if (promotion.getMember().getId().equals(member.getId())) {
                    promotionRepository.deleteById(id);
                }
            }
        }catch(NullPointerException e){

        }
        //회원이 존재한다면 삭제처리한다.

    }
}
