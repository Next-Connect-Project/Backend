package com.project.unigram.promotion.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
    private int status;

    public ErrorResponse(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
