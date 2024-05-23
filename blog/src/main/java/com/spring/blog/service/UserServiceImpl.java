package com.spring.blog.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.blog.mapper.UserMapper;
import com.spring.blog.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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

	
	
}
