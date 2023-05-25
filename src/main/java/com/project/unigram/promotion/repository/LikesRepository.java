package com.project.unigram.promotion.repository;

import com.project.unigram.auth.domain.Member;
import com.project.unigram.promotion.domain.Likes;
import com.project.unigram.promotion.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByMemberAndPromotion(Member member, Promotion promotion);

}
