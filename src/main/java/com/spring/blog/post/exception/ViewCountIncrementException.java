package com.spring.blog.post.exception;

import com.spring.blog.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ViewCountIncrementException extends BaseException {
	public ViewCountIncrementException(Long id) {
		super(HttpStatus.NOT_FOUND, String.format("Faild to increase view count for post, postId = %d", id));
	}
}
