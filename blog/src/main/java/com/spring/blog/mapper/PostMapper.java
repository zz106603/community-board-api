package com.spring.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.blog.dto.SearchDTO;
import com.spring.blog.vo.PostVO;

@Mapper
public interface PostMapper {

	int createPost(PostVO post);
	
	int updatePost(PostVO post);

	PostVO findById(@Param("id") Long id);

	List<PostVO> findByAll(SearchDTO params);

	int deletePost(PostVO post);

	long findByAllCount(SearchDTO params);

	int updateSelectCount(Long id);

	int updateRecomCount(Long id);
	int deleteRecomCount(Long id);

	
}
