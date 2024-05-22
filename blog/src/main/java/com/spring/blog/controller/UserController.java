package com.spring.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.service.UserService;
import com.spring.blog.util.ApiResponse;
import com.spring.blog.util.ResponseUtil;
import com.spring.blog.vo.UserVO;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	/*
	 * 사용자 회원가입
	 * loginId, password, name, birthday, gender
	 */
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<String>> createPost(@RequestBody UserVO user){
		
		try {
			int createdUser = userService.createUser(user);
			if(createdUser == 1) {
				return ResponseUtil.buildResponse(HttpStatus.CREATED, "User created successfully", "성공");
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User creation failed", "실패");
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User creation failed", e.getMessage());
		}
		
	}
	
		
	
}
