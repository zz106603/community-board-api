package com.spring.blog.service;

import java.util.List;

import com.spring.blog.vo.PostVO;

public interface BlogService{

	public List<PostVO> selectPost();
}
