package com.spring.blog.post.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.common.util.ApiResponse;
import com.spring.blog.common.util.PagingResponse;
import com.spring.blog.common.util.ResponseUtil;
import com.spring.blog.post.dto.SearchDTO;
import com.spring.blog.post.service.PostService;
import com.spring.blog.post.vo.CommentVO;
import com.spring.blog.post.vo.PostVO;
import com.spring.blog.post.vo.RecommendVO;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostService postService;

	/*
	 * 단일 포스팅 조회
	 * {postId, incrementViewCount}
	 */
	@GetMapping("/{postId}")
	public PostVO selectPost(@PathVariable("postId") Long postId, @RequestParam(value="incrementViewCount", defaultValue="true") boolean incrementViewCount){
		logger.info(String.valueOf(incrementViewCount));
		return postService.getPostById(postId, incrementViewCount);
	}
	
	/*
	 * 전체 포스팅 조회
	 */
	@GetMapping("/all")
	public PagingResponse<PostVO> selectPost(SearchDTO params){
		return postService.getPostByAll(params);
	}
	
	/*
	 * 전체 포스팅 카운트
	 */
	@GetMapping("/all/count")
	public Long selectPostCount(SearchDTO params){
		return postService.getPostByAllCount(params);
	}
	
	/*
	 * 포스팅 등록
	 * title, content, writer, category
	 */
	@PostMapping("/create")
	public String createPost(@RequestBody PostVO post){
		postService.createPost(post);
		return "SAVE POST COMPLETE";
	}
	
	
//	/*
//	 * 포스팅 등록
//	 * title, content, writer, category
//	 */
//	@PostMapping("/test")
//	public ResponseEntity<ApiResponse<String>> test() throws InterruptedException{
//		
//		for(int i=0; i<100; i++) {
//			PostVO post = new PostVO();
//			post.setCategory("카테고리");
//			post.setTitle("제목 " + i);
//			post.setContent("내용 " + i);
//			post.setWriter("정윤환");
//			
//			int createdPost = postService.createPost(post);
//			Thread.sleep(500);
//		}
//		
//		return ResponseUtil.buildResponse(HttpStatus.CREATED, "Post created successfully", "성공");
//		
//	}
	
	/*
	 * 포스팅 수정
	 * title, content, writer, category
	 */
	@PutMapping("/update")
	public String updatePost(@RequestBody PostVO post){
		postService.updatePost(post);
		return "UPDATE POST COMPLETE";
	}
	
	/*
	 * 포스팅 삭제
	 * title, content, writer, category
	 */
	@DeleteMapping("/delete")
	public String deletePost(@RequestParam("postId") Long postId){
		postService.deletePost(postId);
		return "DELETE POST COMPLETE";
	}
	
	/*
	 *	-----------------추천---------------------- 
	 */
	
	/*
	 * 포스팅 추천 UP/DOWN
	 */
	@PostMapping("/recom")
	public String recomCountPost(@RequestBody RecommendVO recommend){
		postService.processRecommendation(recommend);
		return "RECOM POST COMPLETE";
		
	}
	
	/*
	 * 포스팅 추천 사용자 조회
	 */
//	@GetMapping("/recom/check")
//	public ResponseEntity<ApiResponse<RecommendVO>> checkPostRecom(@RequestParam("userId") String userId, @RequestParam("postId") Long postId){
//
//		RecommendVO recommend = new RecommendVO();
//		recommend.setUserId(userId);
//		recommend.setPostId(postId);
//		
//		RecommendVO recom = postService.getPostRecomByUserIdAndPostId(recommend);
//		System.out.println(recom);
//		
//		if(recom != null) {
//			return ResponseUtil.buildResponse(HttpStatus.OK, "Post recommendation selected successfully", recom);
//		}else {
//			return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Post recommendation not found with id: " + recommend.getPostId(), recom);
//		}
//		
//	}
	
	@GetMapping("/recom/check")
	public RecommendVO checkPostRecom(@RequestParam("userId") String userId, @RequestParam("postId") Long postId){
		return postService.getPostRecomByUserIdAndPostId(userId, postId);
	}
	
	/*
	 * 댓글 등록
	 */
	@PostMapping("/comment/create")
	public String createComment(@RequestBody CommentVO comment){
		postService.createComment(comment);
		return "SAVE COMMENT COMPLETE";
	}
	
	/*
	 * 포스팅 댓글 조회
	 */
	@GetMapping("/comment/{postId}")
	public List<CommentVO> selectComment(@PathVariable("postId") Long postId){
		return postService.getCommentById(postId);
	}
	
	/*
	 * 댓글 삭제
	 */
	@DeleteMapping("/comment/delete")
	public String deleteComment(@RequestParam("commentId") Long commentId){
		postService.deleteComment(commentId);
		return "DELETE COMMENT COMPLETE";
	}
	
}
