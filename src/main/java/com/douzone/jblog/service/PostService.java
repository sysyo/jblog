package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVO;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	public boolean writePost(PostVO vo) {
		
		return postRepository.insert(vo);
	}

}
