package com.project.unigram;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public ResponseEntity<String> isMain() {
        String where = "is SPRING server on 8080 port";
        return ResponseEntity.ok(where);
    }

}
