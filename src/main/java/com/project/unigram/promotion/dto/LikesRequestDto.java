package com.project.unigram.promotion.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesRequestDto {
    @NotNull //notEmpty = String타입에 쓰이는 어노테이션
    private Long memberId;
    @NotNull
    private Long promotionId;

    public LikesRequestDto(Long memberId, Long promotionId) {
        this.memberId = memberId;
        this.promotionId = promotionId;
    }


}
