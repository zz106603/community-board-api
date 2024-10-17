package com.spring.blog.common.config.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	// permitAll()로 설정된 엔드포인트인지 확인하는 메서드
	// 이미 SecurityConfig에서 permitAll을 설정하기 때문에 사용하지 않음
	private boolean isPermitAllEndpoint(String requestURI) {
	    // 허용된 경로를 지정하고, requestURI와 일치하는지 확인
//	    return Arrays.asList("/api/auth/login",
//	    		"/api/auth/create",
//	    		"/api/auth/refresh",
//	    		"/login",
//				"/swagger-ui/index.html",
//	    		"/").contains(requestURI);
		return requestURI.startsWith("/api/auth") ||
				requestURI.equals("/") ||
				requestURI.startsWith("/") ||
				requestURI.startsWith("/swagger-ui") ||
				requestURI.startsWith("/v3/api-docs") ||
				requestURI.startsWith("/oauth2");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String requestURI = httpRequest.getRequestURI();

	    // permitAll()로 설정된 엔드포인트에 대해서는 JWT 검증을 수행하지 않고 다음 필터로 요청을 전달
	    if (isPermitAllEndpoint(requestURI)) {
	        chain.doFilter(request, response);
	        return;
	    }
		
		try {
			// 1. Request Header에서 JWT 토큰 추출
			String token = resolveToken((HttpServletRequest) request);

			// 토큰이 없는 경우 401 응답
            if (token == null || token.isEmpty()) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없습니다.");
                return;
            }

            // 토큰이 유효하지 않은 경우 401 응답
            if (!jwtTokenProvider.validateToken(token)) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            }
			
			// 2. validateToken으로 토큰 유효성 검사
			if (token != null && jwtTokenProvider.validateToken(token)) {
				// 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch(JwtException | IllegalArgumentException e) {
			logger.info(e.getMessage());
			SecurityContextHolder.clearContext();
			((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
			return;
		}
		chain.doFilter(request, response);
	}

	// Request Header에서 토큰 정보 추출
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}