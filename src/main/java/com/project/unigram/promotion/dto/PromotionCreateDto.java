package com.project.unigram.promotion.dto;

import com.project.unigram.promotion.domain.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String title;
    private String content;
    private String abstractContent;

    public static PromotionCreateDto toDto(Promotion promotion){

        return new PromotionCreateDto(
                promotion.getTitle(),
                promotion.getContent(),
                promotion.getAbstractContent()
        );
    }


}
