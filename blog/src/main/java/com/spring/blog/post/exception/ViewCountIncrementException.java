package com.spring.blog.post.exception;

import com.spring.blog.common.exception.BaseException;

public class ViewCountIncrementException extends BaseException {
	public ViewCountIncrementException(Long id) {
		super(String.format("Faild to increase view count for post, postId = %d", id));
	}
}
