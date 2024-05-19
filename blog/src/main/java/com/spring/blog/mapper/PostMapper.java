package com.spring.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.blog.vo.PostVO;

@Mapper
public interface PostMapper {

	List<PostVO> selectPostList();
}
