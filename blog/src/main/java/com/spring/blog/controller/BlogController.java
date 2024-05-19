package com.spring.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.service.BlogService;
import com.spring.blog.vo.PostVO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blog")
public class BlogController {

	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/test")
    public ResponseEntity<?> selectMember(HttpServletRequest request) {
		List<PostVO> result = blogService.selectPost();
		
		return ResponseEntity.ok(result);
    }
}
