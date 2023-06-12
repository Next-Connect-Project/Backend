package com.project.unigram.auth.exception;

import com.project.unigram.global.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenInvalidExceptionHandler {

	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<ResponseError> recruitException(TokenInvalidException e) {
		ResponseError responseError = new ResponseError(e.getErrorCode(), e.getMessage());
		return new ResponseEntity<>(responseError, HttpStatus.UNAUTHORIZED);
	}

}
