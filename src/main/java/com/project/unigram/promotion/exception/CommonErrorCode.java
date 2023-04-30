package com.project.unigram.promotion.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode {
    Not_Empty(400,"title or content is empty.");
    private int code;
    private final String message;

    CommonErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
