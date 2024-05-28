package com.spring.blog.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.config.jwt.JwtToken;
import com.spring.blog.config.jwt.JwtTokenProvider;
import com.spring.blog.service.AuthService;
import com.spring.blog.util.ApiResponse;
import com.spring.blog.util.ResponseUtil;
import com.spring.blog.vo.UserVO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthService authService;
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
	/*
	 * 사용자 회원가입
	 * loginId, password, name, birthday, gender, email, phone
	 */
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<String>> createUser(@RequestBody UserVO user){
		
		try {
			int createdUser = authService.createUser(user);
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
	
	/*
	 * AccessToken 재발급
	 */
	@PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshAccessToken(@RequestBody JwtToken token) {
        String refreshToken = token.getRefreshToken();

        try {
        	String newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken);
			
			if(newAccessToken != null) {
				return ResponseUtil.buildResponse(HttpStatus.OK, "User login successfully", newAccessToken);
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User login failed", null);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User login failed", null);
		}
    }
	
	/*
	 * 사용자 조회 TEST
	 * loginId
	 */
	@GetMapping("/test")
	public ResponseEntity<ApiResponse<Optional<UserVO>>> selectUser(@RequestParam("loginId") String loginId){
		
		try {
			Optional<UserVO> user = authService.findOne(loginId);
			if(user != null) {
				return ResponseUtil.buildResponse(HttpStatus.CREATED, "User created successfully", user);
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User creation failed", null);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User creation failed", null);
		}
		
	}
	
	
	/*
	 * 사용자 login
	 */
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<JwtToken>> login (@RequestBody UserVO user){
		
		try {
			JwtToken token = authService.signIn(user.getLoginId(), user.getPassword());
			
			if(token != null) {
				return ResponseUtil.buildResponse(HttpStatus.OK, "User login successfully", token);
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User login failed", null);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "User login failed", null);
		}
		
	}
		
	
}
