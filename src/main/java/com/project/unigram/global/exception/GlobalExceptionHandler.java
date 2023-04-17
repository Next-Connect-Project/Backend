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
		String[] defaultMessage = e.getFieldError().getDefaultMessage().split(":");
		System.out.println(defaultMessage[0]);
		ResponseError responseError = new ResponseError(ValidationErrorCode.valueOf(defaultMessage[0]), defaultMessage[1]);
		return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ServerException.class)
	public ResponseEntity<ResponseError> serverException(ServerException e) {
		ResponseError responseError = new ResponseError(e.getErrorCode(), e.getMessage());
		return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
