package com.project.unigram.promotion.exception;

import com.project.unigram.global.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class PromotionExceptionHanlder {

    //400 에러
    @ExceptionHandler(PromotionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> PromotionExceptionAdvice(PromotionException e){
        ResponseError responseError = new ResponseError(
                e.getErrorCode(),
                e.getMessage()
        );

        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }
}
