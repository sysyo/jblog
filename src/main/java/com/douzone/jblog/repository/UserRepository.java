package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.exception.UserRepositoryException;
import com.douzone.jblog.vo.UserVO;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;
	
	// 회원 가입
	public boolean insert(UserVO vo) {
		return sqlSession.insert("user.insert", vo) == 1;
	}
	
	// login -> id, password check 
	public UserVO findByIdAndPassword(String id, String password) throws UserRepositoryException {
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("password", password);
		
		
		return sqlSession.selectOne("user.findByIdAndPassword", map);
	}

}
