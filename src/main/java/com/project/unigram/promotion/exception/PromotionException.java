package com.project.unigram.promotion.exception;

import com.project.unigram.global.dto.ResponseError;

public class PromotionException extends RuntimeException{
    private String errorMsg;

    public PromotionException(String errorMsg){
        super(errorMsg);
        this.errorMsg=errorMsg;
    }
}
