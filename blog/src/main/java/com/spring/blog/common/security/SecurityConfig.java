package com.spring.blog.common.security;

import com.spring.blog.common.config.jwt.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.blog.common.config.jwt.JwtAuthenticationFilter;
import com.spring.blog.common.config.jwt.JwtTokenProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	/**
	 * 이 메서드는 정적 자원에 대해 보안을 적용하지 않도록 설정한다.
	 * 정적 자원은 보통 HTML, CSS, JavaScript, 이미지 파일 등을 의미하며, 이들에 대해 보안을 적용하지 않음으로써 성능을 향상시킬 수 있다.
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}


	// 특정 HTTP 요청에 대한 웹 기반 보안 구성
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http	
		.csrf(AbstractHttpConfigurer::disable) //session 기반 인증이 아니기 때문에 disable
		.httpBasic(AbstractHttpConfigurer::disable) // Json을 통한 로그인 진행으로 refresh 토큰 만료 전까지 토큰 인증
		.formLogin(AbstractHttpConfigurer::disable) // Json을 통한 로그인 진행으로 refresh 토큰 만료 전까지 토큰 인증
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/api/posts/**").hasRole("USER")
				.requestMatchers("/api/auth/login",
						"/api/auth/create",
						"/api/auth/refresh",
						"/login",
						"/oauth2/authorization/google",
						"/").permitAll()
				.anyRequest().authenticated())
//		.formLogin(formLogin -> formLogin
//				.disable()
//				.usernameParameter("loginId")
//				.passwordParameter("password")
//				.loginProcessingUrl("/login")
//				.successHandler(successHandler) // 커스텀 성공 핸들러 설정
//				.failureHandler(failureHandler) // 커스텀 실패 핸들러 설정
//				.permitAll()
				//				.defaultSuccessUrl("/", false)
//				)
		.oauth2Login(oauth2 -> oauth2
				.authorizationEndpoint(authorization -> authorization
						.baseUri("/oauth2/authorization"))
				.tokenEndpoint(tokenEndpoint -> tokenEndpoint
						.accessTokenResponseClient(accessTokenResponseClient())
				)
				.userInfoEndpoint(userInfo -> userInfo
						.userService(customOAuth2UserService)
				)
				.successHandler((request, response, authentication) -> {
					// OAuth2 로그인 성공 후 JWT 발급
					OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
					JwtToken jwtToken = jwtTokenProvider.createToken(auth); // JWT 발급

					String accessToken = jwtToken.getAccessToken();
					String refreshToken = jwtToken.getRefreshToken();
					String loginId = jwtToken.getLoginId();

					// React 프론트엔드로 리다이렉트하면서 토큰 전달
					String targetUrl = "http://localhost:3000/oauth2/redirect?accessToken=" + accessToken + "&refreshToken=" + refreshToken + "&loginId=" + loginId;
					response.sendRedirect(targetUrl);

				})
		)
		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
		.logout((logout) -> logout
				.logoutSuccessUrl("/user/login")
				.invalidateHttpSession(true)
				.permitAll())
		.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
		
		return http.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf((csrf) -> csrf.disable())
		.cors((c) -> c.disable())
		.headers((headers) -> headers.disable());
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	//OAuth 2.0 인증 흐름에서 액세스 토큰을 처리하는 클라이언트
	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		return new DefaultAuthorizationCodeTokenResponseClient();
	}

}