package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private BlogRepository blogRepository;
	
	// 회원 가입
	public void join(UserVO vo) {
		userRepository.insert(vo);
		BlogVO blogVO = new BlogVO();
		blogVO.setId(vo.getId());
		blogVO.setTitle(vo.getName() + "의 블로그");
		blogVO.setLogo("/upload/images/luffy.png");
		
		blogRepository.insert(blogVO);
	}
	
	// 회원 가입 시 중복 체크
	public UserVO getUser(String id) {
		
		return userRepository.findById(id);
	}
	
	// login -> id, password check 
	public UserVO getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}

	

}
