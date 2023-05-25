package com.project.unigram.promotion.controller;

import com.project.unigram.global.dto.ResponseSuccess;
import com.project.unigram.promotion.dto.LikesRequestDto;
import com.project.unigram.promotion.dto.PromotionDto;
import com.project.unigram.promotion.service.LikesService;
import com.project.unigram.promotion.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController //REST API를 처리하는 controller로 등록 어노테이션
@RequiredArgsConstructor
@RequestMapping("/api/promotion") //URI 지정 어노테이션
public class LikesController {
    @Autowired
    private LikesService likesService;

    @Autowired
    private PromotionService promotionService;

    //추천 또는 추천 해제
    @ApiOperation(value="추천 기능", notes="추천 수를 높인다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/like")
    public ResponseSuccess getPromotion(@Valid @RequestBody LikesRequestDto likesRequestDto){

        PromotionDto promotionDto = likesService.likeUpdate(likesRequestDto);
        String message = "";
        if(promotionDto.isLikeStatus()==true){
            message = "홍보 게시물 추천 true 적용하였습니다.";
        }else{
            message = "홍보 게시물 추천 false 적용하였습니다.";
        }

        return new ResponseSuccess(200, message,promotionDto.isLikeStatus());
    }
}
