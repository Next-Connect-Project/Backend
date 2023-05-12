package com.project.unigram.promotion.exception;

import com.project.unigram.global.dto.ErrorCode;
import com.project.unigram.global.dto.ResponseError;
import lombok.Getter;

@Getter
public class PromotionException extends RuntimeException{
    private String errorMsg;
    private ErrorCode errorCode;

    public PromotionException(String errorMsg, CommonErrorCode errorCode){
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
}
