package com.project.unigram.promotion.dto;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.domain.Promotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDto {

    private Long id;

//    private LocalDate createdAt;

    private String title;

    private Member member;

    private String content;

//    private LocalDate modifiedAt;

//    @Builder //PromotionDto 객체 생성자 작성
//    public PromotionCreateRequestDto(Member member, String title, String content){
//        this.member=member;
//        this.title=title;
//        this.content=content;
//    }

    //빌더 클래스 생성자로 빌더 객체 생성

    //toDto 메서드를 만들어, Promotion 객체만 넣으면 바로 PromotionDto를 만들도록 하였다.
    public static PromotionDto toDto(Promotion promotion){
        return new PromotionDto(
                promotion.getPostId(),
                promotion.getTitle(),
                promotion.getMember(),
                promotion.getContent()
        );
    }

}
