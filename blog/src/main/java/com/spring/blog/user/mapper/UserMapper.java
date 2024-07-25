package com.spring.blog.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.vo.PostVO;
import com.spring.blog.user.vo.UserVO;

@Mapper
public interface UserMapper {

	int createUser(UserVO user);

	UserVO findById(String loginId);

	
}
