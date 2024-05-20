package com.spring.blog.service;

import java.util.List;

import com.spring.blog.vo.PostVO;

public interface PostService{

	public PostVO getPostById(Long id);

	public List<PostVO> getPostByAll();

	public int createPost(PostVO post);

	public int updatePost(PostVO post);

	public int deletePost(Long id);

	public long getPostByAllCount();
	
}
