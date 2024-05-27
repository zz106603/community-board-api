package com.spring.blog.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.blog.config.jwt.JwtToken;
import com.spring.blog.config.jwt.JwtTokenProvider;
import com.spring.blog.mapper.UserMapper;
import com.spring.blog.vo.UserVO;

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
	public int createUser(UserVO user) {
		
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
			int res = userMapper.createUser(user);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
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
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return Optional.empty();
		}
	}
	
	/*
	 * 로그인
	 */
	@Override
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

	
	
}
