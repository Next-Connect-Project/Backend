package com.project.unigram.promotion.exception;

import com.project.unigram.global.dto.ErrorCode;

public enum CommonErrorCode implements ErrorCode {

    //PROMOTION
    Title_Is_Not_Empty(400,"NO_TITLE"),
    Content_Is_Not_Empty(400, "NO_CONTENT"),
    Abstract_Is_Not_Empty(400,"NO_ABSTRACT_CONTENT"),
    PostId_Is_Not_Valid(400, "NO_PROMOTION_ID"),
    No_Member_Id(400, "NO_MEMBER_ID");

    private int resultCode; //HttpStatus 상태코드
    private String errorCode; //에러코드

    CommonErrorCode(int resultCode, String errorCode) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
    }

    @Override
    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String getCode() {
        return errorCode;
    }
}
