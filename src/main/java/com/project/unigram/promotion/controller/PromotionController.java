package com.project.unigram.promotion.controller;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.Response;
import com.project.unigram.promotion.dto.PromotionCreateDto;
import com.project.unigram.promotion.exception.PromotionException;
import com.project.unigram.promotion.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController //REST API를 처리하는 controller로 등록 어노테이션
@RequiredArgsConstructor
@RequestMapping("/api/promotion") //URI 지정 어노테이션
public class PromotionController {
    private final PromotionService promotionService;
    private final MemberService memberService;

    //전체 게시글 조회
    @ApiOperation(value="전체 게시글 보기", notes="전체 게시글을 조회한다.")
    //API 명세서 Swagger에서 타이틀, 설명을 걸어주는 어노테이션
    @ResponseStatus(HttpStatus.OK)
    //정상 실행시 상태코드를 200으로 주는 코드
    @GetMapping("/resources")
    public Response<?> findAll(){
        return new Response<>("200","전체 게시물 리턴",promotionService.getPromotions());
    }


    //개별 게시글 조회
    @ApiOperation(value="개별 게시글 보기", notes="개별 게시글을 조회한다")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/detail/{memberId}")
    //
    public Response getPromotion(@PathVariable("memberId") Long memberId){
        return new Response("200", "개별 게시물 리턴", promotionService.getPromotion(memberId));
    }


    //게시글 작성
    @ApiOperation(value="게시글 작성", notes="게시글을 작성한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Response write(@RequestBody @Valid PromotionCreateDto promotionCreateDto){
        if(promotionCreateDto.getTitle()==null) {
            throw new PromotionException();
        }

        //@RequestBody를 쓰면 Json을 파싱
        Member member =memberService.getMember();
        return new Response("200", "글 작성 성공",promotionService.write(promotionCreateDto, member));
    }

}