package com.project.unigram.promotion.service;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.repository.PromotionRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final MemberService memberService;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository, MemberService memberService) {
        this.promotionRepository = promotionRepository;
        this.memberService=memberService;

    }

    //전체 게시물 조회
    @Transactional(readOnly = true)
    public List<PromotionDto> getPromotions() {

        List<Promotion> promotions = promotionRepository.findAll();
        List<PromotionDto> promotionDtos = new ArrayList<>();
        List<PromotionDto> promotionList=new ArrayList<>();
        if(promotions.size()!=0){
            promotions.forEach(s -> promotionDtos.add(PromotionDto.toDto(s)));

            //최신순 정렬
            Comparator<PromotionDto> comparingDateReverse = Comparator.comparing(PromotionDto::getCreatedAt, Comparator.naturalOrder());
            promotionList = promotionDtos.stream()
                    .sorted(comparingDateReverse).collect(Collectors.toList());
        }

        return promotionList;
    }

    //개별 게시물 조회
    @Transactional(readOnly = true)
    public PromotionDto getPromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Promotion Id를 찾을 수 없습니다.");
        });
        PromotionDto promotionDto = PromotionDto.toDto(promotion);
        return promotionDto;
    }

    //게시물 작성
    @Transactional
    public PromotionDto write(PromotionCreateDto promotionCreateDto, Member member) {
        Promotion promotion = new Promotion(member, promotionCreateDto.getTitle(), promotionCreateDto.getContent(), promotionCreateDto.getAbstractContent());

        promotionRepository.save(promotion);
        return PromotionDto.toDto(promotion);

    }

    //게시글 수정
    @Transactional
    public PromotionDto update(Long id, PromotionDto promotionDto){
        Promotion promotion=promotionRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다");
        });

        Member member= memberService.getMember();
        if(promotion.getMember().getId() == member.getId()) {
            promotion.setTitle(promotionDto.getTitle());
            promotion.setContent(promotionDto.getContent());
            promotion.setAbstractContent(promotionDto.getAbstractContent());
            promotion.setMember(member);
        }
        return PromotionDto.toDto(promotion);

    }


    //게시글 삭제
    @Transactional
    public void delete(Long id) {
        //게시글이 존재하는지 먼저 확인하고 없다면 오류 처리를 한다.
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Promotion Id를 찾을 수 없습니다.");
        });

        //게시글 아이디를 작성한 사람과 현재 로그인한 사람이 같으면 삭제처리를 한다.
        Member member = memberService.getMember();
        if (member!=null) {
            if (promotion.getMember().getId() == member.getId())
                promotionRepository.deleteById(id);
        }
    }
}
