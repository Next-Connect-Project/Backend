package com.project.unigram.promotion.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode {

    //PROMOTION
    Title_Or_Content_Is_Not_Empty("400", "BAD_REQUEST","제목 또는 내용이 비워져있습니다.");

    private String resultCode;
    private String errorCode;
    private final String errorMsg;

    CommonErrorCode(String resultCode, String errorCode, String errorMsg) {
        this.resultCode = resultCode;
        this.errorCode=errorCode;
        this.errorMsg = errorMsg;
    }
}
