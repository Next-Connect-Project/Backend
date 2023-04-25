package com.project.unigram.promotion.controller;

import com.project.unigram.promotion.Response;
import com.project.unigram.promotion.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    //전체 게시글 조회
    @ApiOperation(value="전체 게시글 보기", notes="전체 게시글을 조회한다.")
    //API 명세서 Swagger에서 타이틀, 설명을 걸어주는 어노테이션
    @ResponseStatus(HttpStatus.OK)
    //정상 실행시 상태코드를 200으로 주는 코드
    @GetMapping("/api/promotion/resources")
    public Response<?> findAll(){
        return new Response<>("200","전체 게시물 리턴",promotionService.getPromotions());
    }

    //개별 게시글 조회
    @ApiOperation(value="개별 게시글 보기", notes="개별 게시글을 조회한다")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/promotion/detail/{memberId}")
    public Response getPromotion(@PathVariable("memberId") Long id){
        return new Response("200", "개별 게시물 리턴", promotionService.getPromotion(id));
    }
}