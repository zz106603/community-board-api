package com.spring.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.blog.vo.PostVO;

@Mapper
public interface PostMapper {

	List<PostVO> selectPostList();

	int createPost(PostVO post);

	PostVO findById(@Param("id") Long id);

	List<PostVO> findByAll();
}
