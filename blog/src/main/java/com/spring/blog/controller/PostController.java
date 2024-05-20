package com.spring.blog.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.service.PostService;
import com.spring.blog.util.ApiResponse;
import com.spring.blog.util.ResponseUtil;
import com.spring.blog.vo.PostVO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/test")
    public ResponseEntity<?> selectMember(HttpServletRequest request) {
		List<PostVO> result = postService.selectPost();
		
		return ResponseEntity.ok(result);
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
	 * 단일 포스팅 조회
	 * {postId}
	 */
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<PostVO>>> selectPost(){

		List<PostVO> post = postService.getPostByAll();
		if(post != null) {
			return ResponseUtil.buildResponse(HttpStatus.OK, "Posts selected successfully", post);
		}else {
			return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Posts not found", post);
		}
		
	}
}
