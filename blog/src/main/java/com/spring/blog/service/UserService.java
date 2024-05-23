package com.spring.blog.service;

import java.util.Optional;

import com.spring.blog.dto.SearchDTO;
import com.spring.blog.util.PagingResponse;
import com.spring.blog.vo.PostVO;
import com.spring.blog.vo.UserVO;

public interface UserService{

	int createUser(UserVO user);

	Optional<UserVO> findOne(String loginId);


}
