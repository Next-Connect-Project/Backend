package com.project.unigram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class UnigramApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnigramApplication.class, args);
	}

}
