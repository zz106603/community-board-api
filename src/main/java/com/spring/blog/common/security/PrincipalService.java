package com.spring.blog.common.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.blog.user.service.AuthService;
import com.spring.blog.user.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
public class PrincipalService implements UserDetailsService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		Optional<UserVO> findOne = userService.findOne(loginId);
		logger.info("findOne : {}",findOne.toString());
		UserVO user = findOne.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 ID입니다."));

		return User.builder()
				.username(user.getLoginId())
				.password(user.getPassword())
				.roles(user.getRoles())
				.build();
	}
	
	public UserVO findByUsername(String loginId) {
		Optional<UserVO> findOne = userService.findOne(loginId);
		logger.info("findOne : {}",findOne.toString());
		
		UserVO user = findOne.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 ID입니다."));
		
		return user;
    }

    public UserDetails createUserDetails(String loginId, String password, PasswordEncoder passwordEncoder) {
    	return User.builder()
    			.username(loginId)
    			.password(passwordEncoder.encode(password))
    			.roles("USER")
    			.build();
    }


}
