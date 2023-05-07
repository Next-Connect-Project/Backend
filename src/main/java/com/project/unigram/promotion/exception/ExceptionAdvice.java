package com.project.unigram.promotion.exception;

import com.project.unigram.promotion.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    //400 에러
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentExceptionAdvice(IllegalArgumentException e){
        return new ErrorResponse(
                CommonErrorCode.Title_Or_Content_Is_Not_Empty.getResultCode(),
                CommonErrorCode.Title_Or_Content_Is_Not_Empty.getErrorCode(),
                CommonErrorCode.Title_Or_Content_Is_Not_Empty.getErrorMsg()
        );
    }
}
