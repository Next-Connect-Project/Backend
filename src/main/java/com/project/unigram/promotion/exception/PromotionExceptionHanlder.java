package com.project.unigram.promotion.exception;

import com.project.unigram.global.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class PromotionExceptionHanlder {
    PromotionErrorCode promotionErrorCode=new PromotionErrorCode();
    //400 에러
    @ExceptionHandler(PromotionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError PromotionExceptionAdvice(PromotionException e){
        return new ResponseError(
                promotionErrorCode,
                e.getMessage()
        );
    }
}
