package com.spring.blog.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blog.dto.Pagination;
import com.spring.blog.dto.SearchDTO;
import com.spring.blog.mapper.CommentMapper;
import com.spring.blog.mapper.PostMapper;
import com.spring.blog.mapper.RecommendMapper;
import com.spring.blog.util.PagingResponse;
import com.spring.blog.vo.CommentVO;
import com.spring.blog.vo.PostVO;
import com.spring.blog.vo.RecommendVO;

@Service
public class PostServiceImpl implements PostService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private RecommendMapper recommendMapper;
	
	@Autowired
	private CommentMapper commentMapper;	

	/*
	 * 단일 포스트 조회
	 */
	@Override
	public PostVO getPostById(Long id) {
		
		try {
			PostVO post = postMapper.findById(id);
			return post;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return null;
		}
	}

	/*
	 * 전체 포스트 조회
	 */
	@Override
	public PagingResponse<PostVO> getPostByAll(SearchDTO params) {
		
		try {
			/*
			 * orderType: 정렬 기준
			 * 1: 최신순
			 * 2: 오래된순
			 * 3: 조회순
			 * 4: 추천순
			 */
			if(params.getOrderType() == 1) {
				params.setOrderCol("write_date DESC");
			}else if(params.getOrderType() == 2) {
				params.setOrderCol("write_date ASC");
			}else if(params.getOrderType() == 3) {
				params.setOrderCol("select_count DESC");
			}else if(params.getOrderType() == 4) {
				params.setOrderCol("recom_count DESC");
			}
			logger.info(params.toString());
			
			// 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
			long count = postMapper.findByAllCount(params);
			if(count < 1) {
				return new PagingResponse<>(Collections.emptyList(), null);
			}
			
			// Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
			Pagination pagination = new Pagination(count, params);
			params.setPagination(pagination);
			
			// 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
			List<PostVO> posts = postMapper.findByAll(params);
			logger.info(posts.toString());
			return new PagingResponse<>(posts, pagination);
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return null;
		}
		
	}

	/*
	 * 전체 포스트 개수 조회
	 */
	@Override
	public long getPostByAllCount(SearchDTO params) {
		
		try {
			long count = postMapper.findByAllCount(params);
			return count;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}

	
	/*
	 * 포스트 등록
	 */
	@Override
	public int createPost(PostVO post) {
		
		try {
			post.setDeleteYn("N");
			LocalDateTime now = LocalDateTime.now();
			post.setWriteDate(now);
			post.setUpdateDate(now);
			post.setSelectCount(0);
			post.setRecomCount(0);
			int res = postMapper.createPost(post);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	/*
	 * 포스트 업데이트
	 */
	@Override
	public int updatePost(PostVO post) {
		
		try {
			post.setUpdateDate(LocalDateTime.now());
			int res = postMapper.updatePost(post);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}


	/*
	 * 포스팅 삭제
	 */
	@Override
	public int deletePost(Long id) {
		
		try {
			
			PostVO post = new PostVO();
			post.setId(id);
			post.setDeleteDate(LocalDateTime.now());
			post.setDeleteYn("Y");
			int res = postMapper.deletePost(post);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	/*
	 * 포스트 조회수 증가
	 */
	@Override
	public int selectCountIncrease(Long id) {
		try {
			int res = postMapper.updateSelectCount(id);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	/*
	 * ---------------------------아래부터 추천 테이블 관련----------------------------------- 
	 */
	
	/*
	 * 포스트 추천수 증가
	 */
	@Override
	public int recomCountIncrease(Long postId) {
		try {
			int res = postMapper.updateRecomCount(postId);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	/*
	 * 포스트 추천수 감소
	 */
	@Override
	public int recomCountDecrease(Long postId) {
		try {
			int res = postMapper.deleteRecomCount(postId);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	/*
	 * 포스트 추천 사용자 정보 저장
	 */
	@Override
	public int recomUserInfoUpdate(RecommendVO recommend) {
		
		try {
			recommend.setRecommendDate(LocalDateTime.now());
			recommend.setDeleteYn("N");
			int res = recommendMapper.updateRecomUserInfo(recommend);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	/*
	 * 포스트 추천 사용자 정보 삭제
	 */
	@Override
	public int recomUserInfoDelete(RecommendVO recommend) {
		
		try {
			recommend.setDeleteDate(LocalDateTime.now());
			recommend.setDeleteYn("Y");
			int res = recommendMapper.deleteRecomUserInfo(recommend);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}

	/*
	 * 추천 사용자 단일 조회
	 */
	@Override
	public RecommendVO getPostRecomByUserIdAndPostId(RecommendVO recommend) {
		
		try {
			RecommendVO recom = recommendMapper.findByUserIdAndPostId(recommend);
			return recom;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return null;
		}
	}

	/*
	 * 댓글 등록
	 */
	@Override
	public int createComment(CommentVO comment) {
		try {
			comment.setDeleteYn("N");
			LocalDateTime now = LocalDateTime.now();
			comment.setCommentDate(now);
			int res = commentMapper.createComment(comment);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	/*
	 * 댓글 조회
	 */
	@Override
	public List<CommentVO> getCommentById(Long id) {
		
		try {
			List<CommentVO> comments = commentMapper.findById(id);
			return comments;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return null;
		}
	}
	
	/*
	 * 댓글 삭제
	 */
	@Override
	public int deleteComment(Long commentId) {
		try {
			CommentVO comment = new CommentVO();
			comment.setId(commentId);
			comment.setDeleteDate(LocalDateTime.now());
			comment.setDeleteYn("Y");
			int res = commentMapper.deleteComment(comment);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return 0;
		}
	}
	
}
