package com.spring.blog.post.exception;

import com.spring.blog.common.exception.BaseException;

public class PostNotFoundException extends BaseException{
	public PostNotFoundException(Long id) {
		super(String.format("Not Found Post, postId = %d", id));
	}
}
