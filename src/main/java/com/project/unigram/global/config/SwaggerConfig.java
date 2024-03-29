package com.project.unigram.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				       .select()
					   // /api URL과 매칭되는 API 자동 문서화
				       .paths(PathSelectors.ant("/api/**"))
				       .apis(RequestHandlerSelectors.any())
				       .build()
				       .apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Unigram API",
				"Unigram Web Application API Docs",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("heenahan", "https://github.com/heenahan", "mby1997s@gmail.com"),
				"API License",
				"http://localhost:8080",
				Collections.emptyList()
		);
	}
	
}
