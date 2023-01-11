package com.project.unigram;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MainController {

    @GetMapping
    public ResponseEntity<String> sample() {
        String where = "running SPRING server on 8080 port";
        return ResponseEntity.ok(where);
    }

}
