package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Likes;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDetailDto;
import com.project.unigram.promotion.dto.PromotionOverviewDto;
import com.project.unigram.promotion.dto.PromotionTotalDto;
import com.project.unigram.promotion.exception.CommonErrorCode;
import com.project.unigram.promotion.exception.PromotionException;
import com.project.unigram.promotion.repository.LikesRepository;
import com.project.unigram.promotion.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<PromotionOverviewDto> getPromotions(int page, int limit, int sorting) {

        List<Promotion> promotions = promotionRepository.findAll();
        List<PromotionOverviewDto> promotionOverviewDtos = new ArrayList<>();
        List<PromotionOverviewDto> promotionList=new ArrayList<>();
        int firstPageNum=1, lastPageNum=16;
        if(promotions.size()!=0){
            promotions.forEach(s -> promotionOverviewDtos.add(PromotionOverviewDto.toDto(s, likesRepository)));

            //1-16, 17-32 인덱스를 가진 게시글을 보여야 하므로 다음과 같이 페이징 처리를 한다.
            lastPageNum=limit*page;
            firstPageNum=lastPageNum-15;
            if(promotionOverviewDtos.size()<=lastPageNum){
                lastPageNum=promotionOverviewDtos.size();
            }

            if(sorting == 0){
                //최신순 정렬
                promotionList = promotionOverviewDtos.stream()
                        .sorted(Comparator.comparing(PromotionOverviewDto::getCreatedAt, Comparator.reverseOrder()))
                        .collect(Collectors.toList())
                        .subList(firstPageNum-1, lastPageNum);
            }else if(sorting == 1) {
                //추천수순 정렬 + 같은 추천수일 경우 오래된 순 정렬
                promotionList = promotionOverviewDtos.stream()
                        .sorted(Comparator.comparing(PromotionOverviewDto::getLikeCount, Comparator.reverseOrder()).thenComparing(PromotionOverviewDto::getCreatedAt))
                        .collect(Collectors.toList())
                        .subList(firstPageNum - 1, lastPageNum);
            }else if(sorting == 2){
                //추천수순 정렬 + 최신순 정렬
                promotionList = promotionOverviewDtos.stream()
                        .sorted(Comparator.comparing(PromotionOverviewDto::getLikeCount, Comparator.reverseOrder()).thenComparing(PromotionOverviewDto::getCreatedAt, Comparator.reverseOrder()))
                        .collect(Collectors.toList())
                        .subList(firstPageNum - 1, lastPageNum);
            }

        }

        return promotionList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PromotionOverviewDto> getFourPromotions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = now.minusWeeks(2);

        List<Promotion> promotionList = promotionRepository.findAllByCreatedAtBetween(startDateTime, now);
        if(promotionList.size() == 0){
            throw new PromotionException("게시물이 존재하지 않습니다.",CommonErrorCode.Content_Is_Not_Empty);
        }

        List<PromotionOverviewDto> promotionOverviewDtoList = new ArrayList<>();
        List<PromotionOverviewDto> finalPromotionDtos = new ArrayList<>();
        int size = 0;
        if(promotionList.size() != 0){
            promotionList.forEach(s -> promotionOverviewDtoList.add(PromotionOverviewDto.toDto(s, likesRepository)));

            if(promotionOverviewDtoList.size()<4){
                size = promotionOverviewDtoList.size();
            }else{
                size = 4;
            }
            finalPromotionDtos = promotionOverviewDtoList.stream()
                    .sorted(Comparator.comparing(PromotionOverviewDto::getLikeCount,Comparator.reverseOrder()))
                    .sorted(Comparator.comparing(PromotionOverviewDto::getCreatedAt, Comparator.reverseOrder()))
                    .collect(Collectors.toList())
                    .subList(0, size);
        }
        return finalPromotionDtos;
    }

    //개별 게시물 조회
    @Override
    @Transactional(readOnly = true)
    public PromotionDetailDto getPromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> {
            throw new PromotionException("Promotion Id를 찾을 수 없습니다.", CommonErrorCode.PostId_Is_Not_Valid);
        });

        PromotionDetailDto promotionDetailDto = PromotionDetailDto.toDto(promotion, likesRepository);
        return promotionDetailDto;
    }

    @Transactional
    public int updateView(Long id){
        return promotionRepository.updateView(id);
    }

    //게시물 작성
    @Override
    @Transactional
    public PromotionTotalDto write(PromotionCreateDto promotionCreateDto, Member member) {

        Promotion promotion = new Promotion(member, promotionCreateDto.getTitle(), promotionCreateDto.getContent(), promotionCreateDto.getAbstractContent());
        promotionRepository.save(promotion);
        likesRepository.save(new Likes(promotion.getMember(), promotion));
        return PromotionTotalDto.toDto(promotion, likesRepository);

    }

    //게시글 수정
    @Override
    @Transactional
    public PromotionOverviewDto update(Long id, PromotionCreateDto promotionCreateDto){
        Promotion promotion=promotionRepository.findById(id).orElseThrow(()->{
            return new PromotionException("Promotion Id를 찾을 수 없습니다", CommonErrorCode.PostId_Is_Not_Valid);
        });

        Member member= memberService.getMember();

        //현재 회원과 게시글 등록 회원이 일치하는지 확인한다.
        try {
            if (promotion.getMember().getId().equals(member.getId())) {
                promotion.setTitle(promotionCreateDto.getTitle());
                promotion.setContent(promotionCreateDto.getContent());
                promotion.setAbstractContent(promotionCreateDto.getAbstractContent());
                promotion.setMember(member);
            }
        }catch(NullPointerException e){

        }
        return PromotionOverviewDto.toDto(promotion, likesRepository);

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
