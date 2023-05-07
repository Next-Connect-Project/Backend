package com.project.unigram.promotion.exception;

import com.project.unigram.global.dto.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionErrorCode implements ErrorCode {
    @Override
    public int getResultCode() {
        return CommonErrorCode.Title_Or_Content_Is_Not_Empty.getResultCode();
    }

    @Override
    public String getCode() {
        return CommonErrorCode.Title_Or_Content_Is_Not_Empty.getErrorCode();
    }
}
