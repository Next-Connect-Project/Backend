package com.project.unigram.promotion.paging;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Criteria {
    private int offset;
    private int limit; //한번에 보여줄 게시물 수

    public Criteria() {
        this(1,16);
    }

    public Criteria(int page, int limit) {
        this.offset=(page-1)*limit;
        this.limit = limit;
    }
}
