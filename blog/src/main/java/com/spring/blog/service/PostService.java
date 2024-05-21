package com.spring.blog.service;

import com.spring.blog.dto.SearchDTO;
import com.spring.blog.util.PagingResponse;
import com.spring.blog.vo.PostVO;

public interface PostService{

	public PostVO getPostById(Long id);

	public int createPost(PostVO post);

	public int updatePost(PostVO post);

	public int deletePost(Long id);

	public long getPostByAllCount(SearchDTO params);
	
	public PagingResponse<PostVO> getPostByAll(SearchDTO params);
}
