package com.project.unigram.promotion.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate //최근 생성날짜 자동 저장
    @DateTimeFormat(pattern="yyyy-MM-dd/HH:mm:ss")
    private LocalDate createdAt; // 글 생성 날짜

    @LastModifiedDate // 최근 수정날짜 자동 저장
    @DateTimeFormat(pattern="yyyy-MM-dd/HH:mm:ss")
    private LocalDate modifiedAt; // 글 수정 날짜
}
