package com.project.unigram.global.exception;

import com.project.unigram.global.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseError> methodArgsNotValidException(MethodArgumentNotValidException e) {
		ResponseError responseError = new ResponseError(400, e.getMessage());
		return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
	}
	
}
