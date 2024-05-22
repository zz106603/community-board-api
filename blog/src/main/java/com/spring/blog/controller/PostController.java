package com.spring.blog.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostService postService;
	
	/*
	 * 단일 포스팅 조회
	 * {postId}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PostVO>> selectPost(@PathVariable("id") Long id){

		PostVO post = postService.getPostById(id);
		if(post != null) {
			return ResponseUtil.buildResponse(HttpStatus.OK, "Post selected successfully", post);
		}else {
			return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Post not found with id: " + id, post);
		}
		
	}
	
	/*
	 * 전체 포스팅 조회
	 */
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<PagingResponse<PostVO>>> selectPost(SearchDTO params){

		PagingResponse<PostVO> post = postService.getPostByAll(params);
		if(post != null) {
			return ResponseUtil.buildResponse(HttpStatus.OK, "Posts selected successfully", post);
		}else {
			return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Posts not found", post);
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
	
	
	
}
