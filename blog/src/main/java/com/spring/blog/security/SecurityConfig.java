package com.spring.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Autowired
	private CustomAuthenticationFailureHandler failureHandler;

	// 스프링 시큐리티 기능 비활성화 (H2 DB 접근을 위해)
	//	@Bean
	//	public WebSecurityCustomizer configure() {
	//		return (web -> web.ignoring()
	//				.requestMatchers(toH2Console())
	//				.requestMatchers("/h2-console/**")
	//		);
	//	}

	// 특정 HTTP 요청에 대한 웹 기반 보안 구성
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http	
		.csrf(AbstractHttpConfigurer::disable) //session 기반 인증이 아니기 때문에 disable
		.httpBasic(AbstractHttpConfigurer::disable) // Json을 통한 로그인 진행으로 refresh 토큰 만료 전까지 토큰 인증
		.formLogin(AbstractHttpConfigurer::disable) // Json을 통한 로그인 진행으로 refresh 토큰 만료 전까지 토큰 인증
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/api/**", "/api/user/**", "/login", "/").permitAll()
				.anyRequest().authenticated())
		.formLogin(formLogin -> formLogin
				.usernameParameter("loginId")
				.passwordParameter("password")
				.loginProcessingUrl("/login")
				.successHandler(successHandler) // 커스텀 성공 핸들러 설정
				.failureHandler(failureHandler) // 커스텀 실패 핸들러 설정
				//				.defaultSuccessUrl("/", false)
				.permitAll())
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
}