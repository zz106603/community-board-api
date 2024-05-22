package com.spring.blog.service;

import com.spring.blog.dto.SearchDTO;
import com.spring.blog.util.PagingResponse;
import com.spring.blog.vo.PostVO;
import com.spring.blog.vo.UserVO;

public interface UserService{

	int createUser(UserVO user);


}
