package com.project.unigram.promotion.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class ErrorResponse {
    //http 상태코드
    private final String resultCode;

    //에러코드
    private final String errorCode;

    //에러메세지
    private final String errorMsg;

    public ErrorResponse(String resultCode, String errorCode, String errorMsg) {
        this.resultCode = resultCode;
        this.errorCode=errorCode;
        this.errorMsg = errorMsg;
    }
}
