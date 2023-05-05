package com.project.unigram.promotion.domain;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="promotion")
@NoArgsConstructor
public class Promotion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //dialect 값에 따른 기본 키 자동 생성 전략을 지정한다.
    @Column(name="post_id", unique = true, nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name="member_name") //단방향 연관관계이며, Member 아이디를 조회하므로 Member 객체를 생성한다.
    private Member member;

    @Column(nullable = false)
    private String title; // 글 제목
    @Column(nullable = false)
    private String content; // 글 내용

    @Column(nullable= false)
    private String abstractContent; // 요약 내용

    @Builder //생성자와 동일한 역할로, 코드 실행 전에 값을 실을 필드를 명확히 인지할 수 있다.
    public Promotion(Member member, String title, String content, String abstractContent){
        this.member=member;
        this.title=title;
        this.content=content;
        this.abstractContent=abstractContent;
    }

}

