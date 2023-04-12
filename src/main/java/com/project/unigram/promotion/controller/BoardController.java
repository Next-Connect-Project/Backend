package com.project.unigram.promotion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping(value = "/api/promotion/resources")
    public String list(){
        return "promotion/resources";
    }

}