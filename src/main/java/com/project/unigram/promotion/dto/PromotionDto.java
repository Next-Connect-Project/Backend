package com.project.unigram.promotion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.unigram.auth.domain.Member;
import com.project.unigram.auth.service.MemberService;
import com.project.unigram.promotion.domain.Likes;
import com.project.unigram.promotion.domain.Promotion;
import com.project.unigram.promotion.repository.LikesRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PromotionDto {


    private Long id;

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

    private String abstractContent;
    private int likeCount;
    private boolean likeStatus;

    private int view;

    //toDto 메서드를 만들어, Promotion 객체만 넣으면 바로 PromotionDto를 만들 수 있다.
    public static PromotionDto toDto(Promotion promotion, LikesRepository likesRepository) {
        String name = "";
        boolean likeStatus = false;
        if (promotion.getMember() == null) {
            name = "no name";
        } else {
            Member member = promotion.getMember();
            name = member.getName();
            likeStatus = likesRepository.findByMemberAndPromotion(promotion.getMember(), promotion).isLikeCheck();
        }

        return new PromotionDto(
                promotion.getPromotionId(),
                promotion.getCreatedAt(),
                promotion.getTitle(),
                promotion.getContent(),
                name,
                promotion.getAbstractContent(),
                promotion.getLikeCount(),
                likeStatus,
                promotion.getView()
        );
    }
}
