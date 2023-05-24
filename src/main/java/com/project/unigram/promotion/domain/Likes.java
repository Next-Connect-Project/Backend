package com.project.unigram.promotion.domain;

import com.project.unigram.auth.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static javax.persistence.FetchType.LAZY;
import javax.persistence.*;

@Entity
@Getter
@Table(name="likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //dialect 값에 따른 기본 키 자동 생성 전략을 지정한다.
    @JoinColumn(name="like_id", unique = true, nullable = false)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Promotion promotion;

    private boolean likeCheck; //true : 좋아요 한 상태, false : 좋아요 안한 상태

    @Builder
    public Likes(Member member, Promotion promotion) {
        this.member = member;
        this.promotion = promotion;
        this.likeCheck = false;
    }

    public void liked(Promotion promotion){
        this.setLikeCheck(true);
    }

    public void unLiked(Promotion promotion){
        this.setLikeCheck(false);
    }

    public Long getLikeId() {
        return likeId;
    }

    public Member getMember() {
        return member;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public boolean isLikeCheck() {
        return likeCheck;
    }

    public void setLikeCheck(boolean likeCheck) {
        this.likeCheck = likeCheck;
    }
}
