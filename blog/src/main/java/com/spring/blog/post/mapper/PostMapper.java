package com.spring.blog.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.vo.CommentVO;
import com.spring.blog.post.vo.PostVO;

@Mapper
public interface PostMapper {

	int createPost(PostVO post);
	
	int updatePost(PostVO post);

	PostVO findById(@Param("id") Long id);

	List<PostVO> findByAll(SearchDTO params);

	int deletePost(PostVO post);

	long findByAllCount(SearchDTO params);

	int increaseViewCount(Long id);

	/*
	 * 추천
	 */
	int updateRecomCount(Long id);
	int deleteRecomCount(Long id);
	
}
