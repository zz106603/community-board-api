package com.spring.blog.user.controller;

import com.spring.blog.common.config.jwt.JwtTokenProvider;
import com.spring.blog.common.security.CustomOAuth2User;
import com.spring.blog.common.security.PrincipalDetails;
import com.spring.blog.user.service.AuthService;
import com.spring.blog.user.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Optional;

@Tag(name = "Auth", description = "Auth 관련 API")
@RestController
@RequestMapping("/api/user")
public class InfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthService authService;

    private final JwtTokenProvider jwtTokenProvider;

    public InfoController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/info")
    public ResponseEntity<UserVO> getUserInfo(@CookieValue(value = "accessToken", required = false) String accessToken) {
        if (accessToken == null) {
            return ResponseEntity.status(401).build(); // 토큰이 없을 때 401 Unauthorized 반환
        }

        try {
            // JwtTokenProvider의 parseClaims 메서드를 사용하여 JWT 복호화
            Claims claims = jwtTokenProvider.parseClaims(accessToken);

            // 클레임에서 loginId 또는 기타 필요한 정보 추출
            String loginId = claims.get("loginId", String.class); // loginId 필드 가져오기

            // loginId를 이용해 DB에서 사용자 정보 조회
            UserVO user = authService.getUserByLoginId(loginId);
            if (user == null) {
                return ResponseEntity.status(404).build(); // 사용자를 찾을 수 없을 때 404 Not Found 반환
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // JWT 토큰이 유효하지 않은 경우 예외 처리
            return ResponseEntity.status(401).body(null);
        }
    }

}
