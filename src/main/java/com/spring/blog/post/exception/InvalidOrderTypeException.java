package com.spring.blog.post.exception;

import com.spring.blog.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidOrderTypeException extends BaseException {
    public InvalidOrderTypeException(int orderType) {
        super(HttpStatus.NOT_FOUND, "Invalid order type: " + orderType);
    }
}