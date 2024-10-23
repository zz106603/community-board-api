package com.spring.blog.user.service;

import java.util.Optional;

import com.spring.blog.common.config.jwt.JwtToken;
import com.spring.blog.common.util.PagingResponse;
import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.vo.PostVO;
import com.spring.blog.user.vo.UserVO;

public interface AuthService{

	void createUser(UserVO user);

	Optional<UserVO> findOne(String loginId);

	JwtToken signIn(String username, String password);

	UserVO getUserByLoginId(String loginId);


}
