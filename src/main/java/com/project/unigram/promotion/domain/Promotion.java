package com.project.unigram.promotion.domain;

import com.project.unigram.auth.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name="promotion")
public class Promotion extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //dialect 값에 따른 기본 키 자동 생성 전략 지정
    @Column(name="post_id", unique = true, nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name="member_id")
    //단방향 연관관계이며, Member 아이디를 조회하므로 Member 객체 생성
    private Member member;

    @Column(length = 30, nullable = false)
    private String title; // 글 제목
    @Column(nullable = false)
    private String content; // 글 내용


//    @Builder
//    public Promotion(Member member, String title, String content){
//        this.member=member;
//        this.title=title;
//        this.content=content;
//    }
    /*
    * @Builder
    * 생성자와 동일한 역할, 코드 실행 전에 값을 실을 필드를 명확히 인지 가능
    * */


}

