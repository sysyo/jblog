package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVO;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public BlogVO find(String id) {
		return sqlSession.selectOne("blog.find", id);
	}

	public boolean update(BlogVO vo) {
		int count = sqlSession.update("blog.update", vo);
		return count == 1;
	}
	
	public boolean insert(BlogVO vo) {
		int count = sqlSession.insert("blog.insert", vo);
		return count == 1; 
	}

}
