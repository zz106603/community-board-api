package com.spring.blog.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blog.mapper.PostMapper;
import com.spring.blog.vo.PostVO;

@Service
public class PostServiceImpl implements PostService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostMapper postMapper;

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
	public List<PostVO> getPostByAll() {
		
		try {
			List<PostVO> posts = postMapper.findByAll();
			return posts;
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
	public long getPostByAllCount() {
		
		try {
			long count = postMapper.findByAllCount();
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

	
}
