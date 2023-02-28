package com.project.unigram.auth.exception;

import com.project.unigram.global.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<ResponseError> tokenInvalidException(TokenInvalidException e) {
		ResponseError error = new ResponseError(401, e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
}
