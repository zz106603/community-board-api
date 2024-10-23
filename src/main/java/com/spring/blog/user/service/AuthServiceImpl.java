package com.spring.blog.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.spring.blog.user.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.blog.common.config.jwt.JwtToken;
import com.spring.blog.common.config.jwt.JwtTokenProvider;
import com.spring.blog.common.exception.BaseException;
import com.spring.blog.user.mapper.UserMapper;
import com.spring.blog.user.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * 사용자 등록
	 */
	@Override
	public void createUser(UserVO user) {

		try {
			/*
			 * 검증하려면 passwordEncoder.matches(들어온 변수, DB에서 가져온 값))
			 */
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setDeleteYn("N");
			LocalDateTime now = LocalDateTime.now();
			user.setCreateDate(now);
			user.setUpdateDate(now);
			user.setRoles("USER");

			userMapper.createUser(user);
		}catch (IllegalArgumentException e) {
			throw new BaseException(HttpStatus.BAD_REQUEST, "Password encoding failed: " + e.getMessage());
		}catch (DataAccessException e) {
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Database operation failed: " + e.getMessage());
		}catch (BaseException e) {
			throw e;
		}catch (Exception e) {	        
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
		}
	}

	/*
	 * 로그인 사용자 아이디로 DB 조회
	 */
	@Override
	public Optional<UserVO> findOne(String loginId) {

		try {
			UserVO user = userMapper.findById(loginId);
			return Optional.ofNullable(user);
		}catch (DataAccessException e) {
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Database operation failed: " + e.getMessage());
		}catch(BaseException e) {
			throw e;
		}catch (Exception e) {
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
		}
	}

	/*
	 * 로그인
	 */
	@Override
	public JwtToken signIn(String username, String password) {

		try {
			// 1. username + password 를 기반으로 Authentication 객체 생성
			// 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

			// 2. 실제 검증. authenticate() 메서드를 통해 요청된 User에 대한 검증 진행
			// authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

			// 3. 인증 정보를 기반으로 JWT 토큰 생성
			JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

			return jwtToken;
		} catch (BadCredentialsException e) {
			// 잘못된 자격 증명 (비밀번호 오류 등) 예외 처리
			throw new BaseException(HttpStatus.UNAUTHORIZED, "Invalid username or password: " + e.getMessage());
		} catch (DisabledException e) {
			// 사용자가 비활성화된 경우 예외 처리
			throw new BaseException(HttpStatus.FORBIDDEN, "User is disabled: " + e.getMessage());
		} catch (LockedException e) {
			// 사용자가 잠긴 경우 예외 처리
			throw new BaseException(HttpStatus.LOCKED, "User account is locked: " + e.getMessage());
		} catch (AuthenticationException e) {
			// 기타 인증 관련 예외 처리
			throw new BaseException(HttpStatus.UNAUTHORIZED, "Authentication failed: " + e.getMessage());
		} catch(BaseException e) {
			throw e;
		}catch(Exception e) {
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public UserVO getUserByLoginId(String loginId) {
		UserVO user = userMapper.findByLoginId(loginId);
		if (user == null) {
			throw new UserNotFoundException(loginId);
		}
		return user;
	}



}
