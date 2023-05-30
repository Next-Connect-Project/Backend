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
    @NotNull
    private Long promotionId;

    public LikesRequestDto(Long promotionId) {
        this.promotionId = promotionId;
    }


}
