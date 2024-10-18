package com.spring.blog.post.exception;

import com.spring.blog.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends BaseException{
	public PostNotFoundException(Long id) {
		super(HttpStatus.NOT_FOUND, String.format("Not Found Post, postId = %d", id));
	}
}
