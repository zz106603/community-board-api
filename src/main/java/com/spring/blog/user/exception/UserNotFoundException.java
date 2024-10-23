package com.spring.blog.user.exception;

import com.spring.blog.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException{
	public UserNotFoundException(String userId) {
		super(HttpStatus.NOT_FOUND, String.format("Not Found User, userId = %S", userId));
	}
}
