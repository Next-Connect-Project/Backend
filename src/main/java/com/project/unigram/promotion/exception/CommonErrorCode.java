package com.project.unigram.promotion.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode {

    //PROMOTION
    Title_Or_Content_Is_Not_Empty(400,"제목 또는 내용이 비어있습니다.");

    private int resultCode; //HttpStatus 상태코드
    private String errorCode; //에러코드

    CommonErrorCode(int resultCode, String errorCode) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
    }
}
