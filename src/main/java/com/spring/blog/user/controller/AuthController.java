package com.spring.blog.user.controller;

import java.util.Optional;

import com.spring.blog.common.model.response.SwaggerCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
		// 모든 쿠키 가져오기
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					String refreshToken = cookie.getValue();
					logger.info("Retrieved refresh token from cookie: {}", refreshToken);

					// Refresh Token 검증 및 새로운 Access Token 생성
					try {
						String newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken);

						// 새로운 Access Token을 쿠키에 설정
						Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
						accessTokenCookie.setHttpOnly(true);
						accessTokenCookie.setSecure(false); // HTTPS 환경에서는 true로 설정
						accessTokenCookie.setPath("/");
						accessTokenCookie.setMaxAge(3600); // Access Token 유효 시간 설정 (1시간)

						// 응답에 쿠키 추가
						response.addCookie(accessTokenCookie);

						return ResponseEntity.ok("SUCCESS");
					} catch (Exception e) {
						// Refresh Token 검증 실패 시
						logger.error("Token verification failed: {}", e.getMessage());
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token. Please log in again.");
					}
				}
			}
		}
		// Refresh Token을 찾을 수 없을 때
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found. Please log in.");
	}
	/*
	 * 사용자 login
	 */
	@PostMapping("/login")
	@Operation(summary = "사용자 로그인", description = "사용자를 로그인합니다.")
	@SwaggerCommonResponse
	public ResponseEntity<JwtToken> login (@RequestBody UserVO user, HttpServletResponse response){

		JwtToken jwtToken = authService.signIn(user.getLoginId(), user.getPassword());

		// 쿠키에 Access Token 설정
		Cookie accessTokenCookie = new Cookie("accessToken", jwtToken.getAccessToken());
		accessTokenCookie.setHttpOnly(true); // JavaScript에서 쿠키 접근 불가, XSS 방지
		accessTokenCookie.setSecure(false); // HTTPS에서만 전송 (개발 단계에서는 false로 설정 가능)
		accessTokenCookie.setPath("/"); // 모든 경로에서 쿠키 사용 가능
		accessTokenCookie.setMaxAge(3600); // 쿠키 유효 시간 설정 (1시간)

		// 쿠키에 Refresh Token 설정
		Cookie refreshTokenCookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(86400); // 1일 유효

		Cookie loginIdCookie = new Cookie("loginId", jwtToken.getLoginId());
		loginIdCookie.setHttpOnly(true);
		loginIdCookie.setSecure(false);
		loginIdCookie.setPath("/");
		loginIdCookie.setMaxAge(60 * 60 * 24);

		// 응답에 쿠키 추가
		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);
		response.addCookie(loginIdCookie);

		return ResponseEntity.ok(jwtToken);
	}

	/*
		사용자 로그아웃
	 */
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletResponse response) {
		// Access Token 쿠키 만료 설정
		Cookie accessTokenCookie = new Cookie("accessToken", null);
		accessTokenCookie.setHttpOnly(true);
		accessTokenCookie.setSecure(false); // 개발 환경에서는 false로 설정 가능
		accessTokenCookie.setPath("/");
		accessTokenCookie.setMaxAge(0); // 쿠키 만료

		// Refresh Token 쿠키 만료 설정
		Cookie refreshTokenCookie = new Cookie("refreshToken", null);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(0); // 쿠키 만료

		// Refresh Token 쿠키 만료 설정
		Cookie loginIdCookie = new Cookie("loginId", null);
		loginIdCookie.setHttpOnly(true);
		loginIdCookie.setSecure(false);
		loginIdCookie.setPath("/");
		loginIdCookie.setMaxAge(0); // 쿠키 만료

		// 응답에 쿠키 추가 (쿠키 삭제)
		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);
		response.addCookie(loginIdCookie);

		return ResponseEntity.ok("SUCCESS");
	}

	//	public JwtToken login (@RequestBody UserVO user){
//		return authService.signIn(user.getLoginId(), user.getPassword());
//	}

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
