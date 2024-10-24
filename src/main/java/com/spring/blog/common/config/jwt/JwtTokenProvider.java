package com.spring.blog.common.config.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import com.spring.blog.common.security.PrincipalDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
    
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Key key;

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // User 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Authentication authentication) {
    	
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        String loginId = authentication.getName();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 3600000);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("loginId", loginId)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
        		.setSubject(authentication.getName())
        		.claim("auth", authorities)
                .setExpiration(new Date(now + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loginId(loginId)
//                .userName(userName)
                .build();
    }

    public JwtToken createToken(OAuth2AuthenticationToken authentication) {
        // OAuth2AuthenticationToken에서 권한 정보 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간
        long now = (new Date()).getTime();

        String loginId = String.valueOf(authentication.getPrincipal().getAttributes().get("email"));

        // Access Token 생성 (1시간 유효)
        Date accessTokenExpiresIn = new Date(now + 3600000); // 1시간
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 이름 설정
                .claim("auth", authorities) // 권한 정보 설정
                .claim("loginId", loginId) // loginId 정보 추가
                .setExpiration(accessTokenExpiresIn) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 및 비밀 키 사용
                .compact();

        // Refresh Token 생성 (1일 유효)
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("loginId", loginId) // loginId 정보 추가
                .setExpiration(new Date(now + 86400000)) // 1일 만료
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // JWT 토큰 정보를 담은 JwtToken 객체 생성 및 반환
        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loginId(String.valueOf(authentication.getPrincipal().getAttributes().get("email"))) // 사용자 로그인 ID 설정
                .build();
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String loginId = claims.get("loginId", String.class);

        // UserDetails 객체를 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails를 구현한 class
//        PrincipalDetails principal = new PrincipalDetails(claims.getSubject(), "", authorities);
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            logger.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            logger.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            logger.info("JWT claims string is empty.", e);
        }
        return false;
    }


    // accessToken
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    
    public String refreshAccessToken(String refreshToken) {
        if (validateToken(refreshToken)) {
            Claims claims = parseClaims(refreshToken);
            String username = claims.getSubject();
           
            // 새로운 Access Token 생성
            long now = (new Date()).getTime();
            Date accessTokenExpiresIn = new Date(now + 86400000); // 1일 유효

            String authorities = claims.get("auth", String.class);
            
            String newAccessToken = Jwts.builder()
                    .setSubject(username)
                    .claim("auth", authorities)
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
            

            return newAccessToken;
        }
        throw new RuntimeException("Invalid refresh token");
    }

}