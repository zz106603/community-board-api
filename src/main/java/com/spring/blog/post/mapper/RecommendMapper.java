package com.spring.blog.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.vo.PostVO;
import com.spring.blog.post.vo.RecommendVO;

@Mapper
public interface RecommendMapper {

	/*
	 * 추천 사용자 정보
	 */
	int updateRecomUserInfo(RecommendVO recommend);
	int deleteRecomUserInfo(RecommendVO recommend);

	RecommendVO findByUserIdAndPostId(RecommendVO recommend);

	

	
}
