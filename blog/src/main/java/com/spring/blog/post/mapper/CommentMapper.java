package com.spring.blog.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.vo.CommentVO;
import com.spring.blog.post.vo.PostVO;
import com.spring.blog.post.vo.RecommendVO;

@Mapper
public interface CommentMapper {

	List<CommentVO> findById(Long id);

	int createComment(CommentVO comment);
	
	int deleteComment(CommentVO comment);


	

	
}
