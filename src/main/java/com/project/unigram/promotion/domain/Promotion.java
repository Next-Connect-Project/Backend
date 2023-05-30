package com.project.unigram.promotion.domain;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.global.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="promotion")
@NoArgsConstructor
public class Promotion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //dialect 값에 따른 기본 키 자동 생성 전략을 지정한다.
    @Column(name="promotion_id", unique = true, nullable = false)
    private Long promotionId;

    @ManyToOne
    @JoinColumn(name="member_name") //단방향 연관관계이며, Member 아이디를 조회하므로 Member 객체를 생성한다.
    private Member member;

    @Column(nullable = false)
    private String title; // 글 제목
    @Column(nullable = false)
    private String content; // 글 내용

    @Column(nullable= false, name = "abstract_content")
    private String abstractContent; // 요약 내용

    @Column(name = "like_count")
    @ColumnDefault("0")
    private int likeCount; //추천수

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; // 조회수


    @Builder //생성자와 동일한 역할로, 코드 실행 전에 값을 실을 필드를 명확히 인지할 수 있다.
    public Promotion(Member member, String title, String content, String abstractContent){
        this.member=member;
        this.title=title;
        this.content=content;
        this.abstractContent=abstractContent;
    }

    @Builder(builderMethodName = "promotionBuilder")
    public Promotion(Long promotionId, Member member, String title, String content, String abstractContent) {
        this.promotionId = promotionId;
        this.member = member;
        this.title = title;
        this.content = content;
        this.abstractContent = abstractContent;
    }

}

