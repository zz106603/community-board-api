package com.spring.blog.user.controller;

import java.util.Optional;

import com.spring.blog.common.model.response.SwaggerCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.common.config.jwt.JwtToken;
import com.spring.blog.common.config.jwt.JwtTokenProvider;
import com.spring.blog.common.security.PrincipalService;
import com.spring.blog.common.util.ApiResponse;
import com.spring.blog.common.util.ResponseUtil;
import com.spring.blog.user.service.AuthService;
import com.spring.blog.user.vo.UserVO;

@Tag(name = "Auth", description = "Auth 관련 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthService authService;
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private PrincipalService principalService;

	/*
	 * 사용자 회원가입
	 * loginId, password, name, birthday, gender, email, phone
	 */
	@PostMapping("/create")
	@Operation(summary = "사용자 회원가입", description = "사용자를 회원가입합니다.")
	@SwaggerCommonResponse //Swagger 공통 응답 어노테이션
	public String createUser(@RequestBody UserVO user){
		authService.createUser(user);
		return "SIGNUP USER COMPLETE";	
	}
	
	/*
	 * AccessToken 재발급
	 */
	@PostMapping("/refresh")
	@Operation(summary = "AccessToken 재발급", description = "AccessToken을 재발급합니다.")
	@SwaggerCommonResponse
    public String refreshAccessToken(@RequestBody JwtToken token) {
        String refreshToken = token.getRefreshToken();
    	return jwtTokenProvider.refreshAccessToken(refreshToken);
    }
	
	/*
	 * 사용자 login
	 */
	@PostMapping("/login")
	@Operation(summary = "사용자 로그인", description = "사용자를 로그인합니다.")
	@SwaggerCommonResponse
	public JwtToken login (@RequestBody UserVO user){
		return authService.signIn(user.getLoginId(), user.getPassword());
	}
	
	/*
	 * 사용자 조회 TEST
	 * loginId
	 */
//	@GetMapping("/test")
//	public Optional<UserVO> selectUser(@RequestParam("loginId") String loginId){
//		return authService.findOne(loginId);
//	}
//
//	@GetMapping("/test2")
//	public String test2(){
//		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.getName() == null) {
//            throw new RuntimeException("No authentication information.");
//        }
//
//        return authentication.getName();
//	}
		
	
}
