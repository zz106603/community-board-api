package com.spring.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blog.mapper.PostMapper;
import com.spring.blog.vo.PostVO;

@Service
public class BlogServiceImpl implements BlogService{
	
	@Autowired
	private PostMapper postMapper;
	
	@Override
	public List<PostVO> selectPost() {
		
		try{
			List<PostVO> res = postMapper.selectPostList();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
