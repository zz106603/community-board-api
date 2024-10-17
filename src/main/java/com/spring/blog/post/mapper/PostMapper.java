package com.spring.blog.post.mapper;

import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

	//Test용
	void deleteAll();
	
}
