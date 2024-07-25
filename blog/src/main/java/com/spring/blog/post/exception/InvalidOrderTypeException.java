package com.spring.blog.post.exception;

import com.spring.blog.common.exception.BaseException;

public class InvalidOrderTypeException extends BaseException {
    public InvalidOrderTypeException(int orderType) {
        super("Invalid order type: " + orderType);
    }
}