package com.project.unigram.promotion.paging;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Criteria {
    private int pageNum;// 페이지 번호
    private int amount; //한번에 보여줄 게시물 수

    public Criteria() {
        this(1,16);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }
}
