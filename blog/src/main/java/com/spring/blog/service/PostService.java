package com.spring.blog.service;

import java.util.List;

import com.spring.blog.vo.PostVO;

public interface PostService{

	public List<PostVO> selectPost();

	public int createPost(PostVO post);

	public PostVO getPostById(Long id);

	public List<PostVO> getPostByAll();
}
