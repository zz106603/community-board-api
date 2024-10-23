package com.spring.blog.user.controller;

import com.spring.blog.common.security.PrincipalDetails;
import com.spring.blog.user.service.AuthService;
import com.spring.blog.user.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Auth", description = "Auth 관련 API")
@RestController
@RequestMapping("/api/user")
public class InfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthService authService;

    @GetMapping("/info")
    public ResponseEntity<UserVO> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.warn("Authentication object is null. User is not authenticated.");
            return ResponseEntity.status(401).build();
        }

        if (!authentication.isAuthenticated()) {
            logger.warn("User is not authenticated. Authentication: " + authentication);
            return ResponseEntity.status(401).build();
        }

        String loginId = null;
        String email = null;

//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//            Object principal = authentication.getPrincipal();
//
//            User user = (User) principal;
//            loginId = user.getUsername();
//        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                User user = (User) principal;
                logger.info(user.toString());
            } else {
                logger.warn("Authentication principal is not an instance of User.");
            }
        }

        logger.info(loginId);
        logger.info(email);

        if (loginId == null) {
            logger.warn("Unable to extract email from authentication object.");
            return ResponseEntity.status(401).build();
        }

        // 서비스 계층을 통해 사용자 정보 조회
        UserVO user = authService.getUserByLoginId(loginId);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).build(); // 사용자 정보가 없을 경우
        }
    }

}
