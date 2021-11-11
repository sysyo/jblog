package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.UserVO;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;

	public BlogVO getBlog(UserVO vo) {
		return blogRepository.find(vo);
	}

	// update는 boolean으로 써야함 -> mysql에서 update 됐을 때 / 안됐을 때 체크
	public boolean update(BlogVO vo) {
		
		return blogRepository.update(vo);
		
	}

}
