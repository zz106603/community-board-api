package com.spring.blog.controller;


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

import com.spring.blog.dto.SearchDTO;
import com.spring.blog.service.PostService;
import com.spring.blog.util.ApiResponse;
import com.spring.blog.util.PagingResponse;
import com.spring.blog.util.ResponseUtil;
import com.spring.blog.vo.PostVO;
import com.spring.blog.vo.RecommendVO;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/test")
	public String test(){

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        
        return authentication.getName();
	}
	
	/*
	 * 단일 포스팅 조회
	 * {postId}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PostVO>> selectPost(@PathVariable("id") Long id, @RequestParam("firstFlag") int firstFlag){

		logger.info(String.valueOf(firstFlag));
		PostVO post = postService.getPostById(id);
		
		if(post != null) {
			if(firstFlag == 1) {
				int selectCount = postService.selectCountIncrease(id);
			}
			return ResponseUtil.buildResponse(HttpStatus.OK, "Post selected successfully", post);
		}else {
			return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Post not found with id: " + id, post);
		}
		
	}
	
	/*
	 * 전체 포스팅 조회
	 */
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<PagingResponse<PostVO>>> selectPost(SearchDTO params){
		
		logger.info(String.valueOf(params.getOrderType()));

		/*
		 * orderType: 정렬 기준
		 * 1: 최신순
		 * 2: 오래된순
		 * 3: 조회순
		 * 4: 추천순
		 */
		PagingResponse<PostVO> post = postService.getPostByAll(params);
		if(post != null) {
			return ResponseUtil.buildResponse(HttpStatus.OK, "Posts selected successfully", post);
		}else {
			return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Posts not found", post);
		}
		
	}
	
	/*
	 * 전체 포스팅 카운트
	 */
	@GetMapping("/all/count")
	public ResponseEntity<ApiResponse<Long>> selectPostCount(SearchDTO params){

		long count = postService.getPostByAllCount(params);
		
		return ResponseUtil.buildResponse(HttpStatus.OK, "Posts selected successfully", count);
	}
	
	/*
	 * 포스팅 등록
	 * title, content, writer, category
	 */
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<String>> createPost(@RequestBody PostVO post){
		
		try {
			int createdPost = postService.createPost(post);
			if(createdPost == 1) {
				return ResponseUtil.buildResponse(HttpStatus.CREATED, "Post created successfully", "성공");
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post creation failed", "실패");
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post creation failed", e.getMessage());
		}
		
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
	public ResponseEntity<ApiResponse<String>> updatePost(@RequestBody PostVO post){
		
		try {
			int updatedPost = postService.updatePost(post);
			if(updatedPost == 1) {
				return ResponseUtil.buildResponse(HttpStatus.CREATED, "Post updated successfully", "성공");
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post update failed", "실패");
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post update failed", e.getMessage());
		}
		
	}
	
	/*
	 * 포스팅 삭제
	 * title, content, writer, category
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<String>> deletePost(@RequestParam("id") Long id){
		
		try {
			int deletedPost = postService.deletePost(id);
			if(deletedPost == 1) {
				return ResponseUtil.buildResponse(HttpStatus.CREATED, "Post deleted successfully", "성공");
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post delete failed", "실패");
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post delete failed", e.getMessage());
		}
	}
	
	/*
	 *	-----------------추천---------------------- 
	 */
	
	/*
	 * 포스팅 추천 UP/DOWN
	 */
	@PostMapping("/recom")
	public ResponseEntity<ApiResponse<String>> recomCountPost(@RequestBody RecommendVO recommend){
		
		try {
			
			int recomPost = 0;
			int recomPostUserInfo = 0;
			if(recommend.getCountFlag() == 1) { //UP
				recomPost = postService.recomCountIncrease(recommend.getPostId());
				recomPostUserInfo = postService.recomUserInfoUpdate(recommend);
				
			}else { //DOWN
				recomPost = postService.recomCountDecrease(recommend.getPostId());
				recomPostUserInfo = postService.recomUserInfoDelete(recommend);
			}
			
			if(recomPost == 1 && recomPostUserInfo == 1) {
				return ResponseUtil.buildResponse(HttpStatus.CREATED, "Post recommendation successfully", "성공");
			}else {
				return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post recommendation failed", "실패");
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Post recommendation failed", e.getMessage());
		}
		
	}
	
	/*
	 * 포스팅 추천 사용자 조회
	 */
	@GetMapping("/recom/check")
	public ResponseEntity<ApiResponse<RecommendVO>> checkPostRecom(@RequestParam("userId") String userId, @RequestParam("postId") Long postId){

		RecommendVO recommend = new RecommendVO();
		recommend.setUserId(userId);
		recommend.setPostId(postId);
		
		RecommendVO recom = postService.getPostRecomByUserIdAndPostId(recommend);
		System.out.println(recom);
		
		if(recom != null) {
			return ResponseUtil.buildResponse(HttpStatus.OK, "Post recommendation selected successfully", recom);
		}else {
			return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Post recommendation not found with id: " + recommend.getPostId(), recom);
		}
		
	}
	
}
