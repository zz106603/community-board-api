package com.spring.blog.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spring.blog.common.model.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex){
		ErrorResponse response = new ErrorResponse(ex.isSuccess(), ex.getDetailMessage());
		
		// 로그에 에러 기록
        logger.error("Error occurred: {}", ex.getDetailMessage());
        
		return new ResponseEntity<>(response, ex.getStatus());
	}

}
