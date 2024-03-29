package com.project.unigram.recruit.exception;

import com.project.unigram.global.dto.ResponseError;
import com.project.unigram.global.exception.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecruitExceptionHandler {
	
	@ExceptionHandler(RecruitException.class)
	public ResponseEntity<ResponseError> recruitException(RecruitException e) {
		ResponseError responseError = new ResponseError(e.getErrorCode(), e.getMessage());
		return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
	}
	
}
