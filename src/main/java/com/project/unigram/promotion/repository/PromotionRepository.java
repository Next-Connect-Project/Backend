package com.project.unigram.promotion.repository;

import com.project.unigram.promotion.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

}
/*CRUD 메서드 자동 생성
* JpaRepository<Entity 클래스, PK 타입>
* @Repository 추가 필요 없음
*/