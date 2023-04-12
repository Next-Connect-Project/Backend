package com.project.unigram.promotion.domain;

import com.project.unigram.auth.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name="promotion")
public class Promotion {
    @Id
    @Column(name="post_id", unique = true, nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name="member_id") //단방향
    private Member member;
//    @JoinColumn(name="member_id")
////    @ManyToOne
//    private Long memberId;
    private LocalDate createdAt;

    @Column(length = 30, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String content;

    private LocalDate modifiedAt;
    private Long likes;

//    private List<Item>items=new ArrayList<>();

}

