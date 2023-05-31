package com.project.unigram.promotion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.repository.LikesRepository;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PromotionDetailDto {

    private Long id;
    private boolean owner;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String title;
    private String content;
    private String name;
    private int likeCount;
    private boolean likeStatus;

    private int view;

    //toDto 메서드를 만들어, Promotion 객체만 넣으면 바로 PromotionDto를 만들 수 있다.
    public static PromotionDetailDto toDto(Promotion promotion, LikesRepository likesRepository, boolean owner) {
        String name = "";
        boolean likeStatus = false;
        if (promotion.getMember() == null) {
            name = "no name";
        } else {
            Member member = promotion.getMember();
            name = member.getName();
            likeStatus = likesRepository.findByPromotion_PromotionId(promotion.getPromotionId()).isLikeCheck();
        }

        return new PromotionDetailDto(
                promotion.getPromotionId(),
                owner,
                promotion.getCreatedAt(),
                promotion.getTitle(),
                promotion.getContent(),
                name,
                promotion.getLikeCount(),
                likeStatus,
                promotion.getView()
        );
    }
}
