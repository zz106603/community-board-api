package com.spring.blog.post.service;

import java.util.List;

import com.spring.blog.common.util.PagingResponse;
import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.vo.CommentVO;
import com.spring.blog.post.vo.PostVO;
import com.spring.blog.post.vo.RecommendVO;

public interface PostService{

	PostVO getPostById(Long id, boolean incrementViewCount);

	void createPost(PostVO post);

	void updatePost(PostVO post);

	void deletePost(Long id);

	long getPostByAllCount(SearchDTO params);
	
	PagingResponse<PostVO> getPostByAll(SearchDTO params);

	void increaseViewCount(Long id);

	/*
	 * 추천 Up/Down
	 */
//	int recomCountIncrease(Long postId);
//	int recomCountDecrease(Long postId);
	
	//추천 UP/DOWN
	void processRecommendation(RecommendVO recommend);

	/*
	 * 추천 테이블 사용자
	 */
//	int recomUserInfoUpdate(RecommendVO recommend);
//	int recomUserInfoDelete(RecommendVO recommend);
	RecommendVO getPostRecomByUserIdAndPostId(String userId, Long postId); //사용자 추천 여부 확인

	/*
	 * 댓글
	 */
	void createComment(CommentVO comment);
	void deleteComment(Long commentId);
	List<CommentVO> getCommentById(Long id);


}
