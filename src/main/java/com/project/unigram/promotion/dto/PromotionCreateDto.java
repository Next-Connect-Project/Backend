package com.project.unigram.promotion.dto;

import com.project.unigram.global.exception.ValidationErrorCode;
import com.project.unigram.promotion.domain.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class PromotionCreateDto {

    @NotBlank(message = "NOT_EMPTY:홍보 게시글의 제목이 비어있습니다.")
    private String title;
    @NotBlank(message = "NOT_EMPTY:홍보 게시글의 내용이 비어있습니다.")
    private String content;
    @NotBlank(message = "NOT_EMPTY:홍보 게시글의 요약정보가 비어있습니다.")
    private String abstractContent;

    public PromotionCreateDto toDto(Promotion promotion){

        return new PromotionCreateDto(
                promotion.getTitle(),
                promotion.getContent(),
                promotion.getAbstractContent()
        );
    }


}
