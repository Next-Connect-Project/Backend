package com.project.unigram.promotion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.domain.Promotion;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@Getter
public class PromotionDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private String title;

    private String content;

    private String name;
    private String abstractContent;

    //toDto 메서드를 만들어, Promotion 객체만 넣으면 바로 PromotionDto를 만들도록 하였다.
    public static PromotionDto toDto(Promotion promotion){
        return new PromotionDto(
                promotion.getPostId(),
                promotion.getCreatedAt(),
                promotion.getTitle(),
                promotion.getContent(),
                promotion.getMember().getName(),
                promotion.getAbstractContent()
        );
    }

}
