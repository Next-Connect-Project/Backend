package com.project.unigram.promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) //null 가지는 필드는 응답에 포함하지 않는다.
@Getter
@AllArgsConstructor
public class Response <T>{
    private String resultCode;
    private String successMsg;
    private T response;
}
