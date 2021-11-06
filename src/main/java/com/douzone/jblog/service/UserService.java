package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	// 회원 가입
	public void join(UserVO vo) {
		userRepository.insert(vo);
	}
	
	// login -> id, password check 
	public UserVO getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}
	
	

}