package com.project.unigram.promotion.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PromotionDto {
    private Long postId;

    private LocalDate createdAt;

    private String title;

    private String content;

    private LocalDate modifiedAt;

    @Builder //PromotionDto 객체 생성자 작성
    public PromotionDto(Long postId, LocalDate createAt, LocalDate modifiedAt, String title, String content){
        this.postId=postId;
        this.createdAt=createAt;
        this.title=title;
        this.modifiedAt=modifiedAt;
        this.content=content;
    }
}
