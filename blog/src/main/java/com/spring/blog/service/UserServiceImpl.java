package com.spring.blog.service;

import java.time.LocalDateTime;

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
			int res = userMapper.createUser(user);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}

	
	
}
