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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/info")
	public ResponseEntity<UserVO> getUserInfo(OAuth2AuthenticationToken authentication) {
		System.out.println(authentication.getPrincipal().toString());

		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(401).build();
		}

		// OAuth2User에서 사용자 정보 가져오기
		OAuth2User oauth2User = authentication.getPrincipal();
		String email = oauth2User.getAttribute("email");

		// 서비스 계층을 통해 사용자 정보 조회
		UserVO user = authService.getUserByEmail(email);

		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(404).build(); // 사용자 정보가 없을 경우
		}
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
