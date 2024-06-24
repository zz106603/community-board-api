package com.spring.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.blog.dto.SearchDTO;
import com.spring.blog.vo.CommentVO;
import com.spring.blog.vo.PostVO;
import com.spring.blog.vo.RecommendVO;

@Mapper
public interface CommentMapper {

	int createComment(CommentVO comment);



	

	
}
