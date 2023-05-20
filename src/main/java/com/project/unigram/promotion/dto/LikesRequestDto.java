package com.project.unigram.promotion.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesRequestDto {
    private Long memberId;
    private Long promotionId;

    public LikesRequestDto(Long memberId, Long promotionId) {
        this.memberId = memberId;
        this.promotionId = promotionId;
    }


}
