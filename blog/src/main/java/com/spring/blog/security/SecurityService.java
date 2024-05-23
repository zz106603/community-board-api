package com.spring.blog.security;

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

import com.spring.blog.service.UserService;
import com.spring.blog.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		Optional<UserVO> findOne = userService.findOne(loginId);
		logger.info("findOne : {}",findOne);
		UserVO user = findOne.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 ID입니다."));

		return User.builder()
				.username(user.getLoginId())
				.password(user.getPassword())
				.roles(user.getRoles())
				.build();
	}

	//	    public UserVO createUser(String loginId, String password, PasswordEncoder passwordEncoder) {
	//	    	UserVO newUser = new UserVO();
	//	    	newUser.setLoginId(loginId);
	//	    	newUser.setPassword(passwordEncoder.encode(password));
	//	    	newUser.setRoles("USER");
	//	        return newUser;
	//	    }


}
