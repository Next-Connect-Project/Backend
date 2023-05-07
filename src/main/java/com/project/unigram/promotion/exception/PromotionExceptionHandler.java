package com.project.unigram.promotion.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestControllerAdvice.class)
public class PromotionExceptionHandler {
    @ExceptionHandler(PromotionException.class) //타겟이 되는 예외가 발생시 메서드 호출
    public String handleExceptionInternal(PromotionException e){
        return e.getMessage();
    }
}
