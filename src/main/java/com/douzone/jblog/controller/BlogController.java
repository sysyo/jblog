package com.douzone.jblog.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.exception.FileUploadException;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.UserVO;
import com.douzone.jblog.vo.PostVO;

@Controller
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private BlogService blogService;

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public String index(@AuthUser UserVO vo, Model model) {

		BlogVO blog = blogService.getBlog(vo);
		List<CategoryVO> list = categoryService.getCategory(vo.getId());
		model.addAttribute("blog", blog);		
		model.addAttribute("list",list);
		return "blog/blog-main";
	}

	// 블로그 관리 페이지 넘어가기
	@RequestMapping("/adminBasic")
	public String blogAdmin(@AuthUser UserVO vo, Model model) {

		BlogVO blog = blogService.getBlog(vo);
		model.addAttribute("blog", blog);

		return "blog/blog-admin-basic";
	}

	// 블로그 관리 페이지에서 update
	@PostMapping("/adminBasic")
	public String blogAdmin(BlogVO vo, @RequestParam("file") MultipartFile file) {

		try {
			String logo = fileUploadService.restoreImage(file);
			vo.setLogo(logo);

		} catch (FileUploadException ex) {
			System.out.println("error : " + ex);
		}

		blogService.update(vo);
		servletContext.setAttribute("blog", vo);

//		System.out.println("blogAdmin - blogAdmin" + vo);

		return "redirect:/blog";
	}
	
	@GetMapping("/adminCategory")
	public String adminCategory(@AuthUser UserVO vo, Model model) {
		
//		System.out.println(vo);
		
		List<CategoryVO> list = categoryService.getCategory(vo.getId()); // -> blogId
		model.addAttribute("list", list);
		
		return "blog/blog-admin-category";
	}
	
	@RequestMapping("/categoryAdd")
	public String add(@AuthUser UserVO userVO, CategoryVO cateVO) {
		cateVO.setBlogId(userVO.getId());
		categoryService.addCategory(cateVO);
		
		return "redirect:/blog/adminCategory";
	}
	
	@RequestMapping("categoryDelete/{no}")
	public String delete(@PathVariable("no") Long no) {
		categoryService.deleteCategory(no);
		
		return "redirect:/blog/adminCategory";
	}
	
	
	// 글 작성 페이지로 이동
	@GetMapping("/write")
	public String blogWrite() {
		
		return "blog/blog-admin-write";
	}
	
	// 글 작성
	@PostMapping("/write")
	public String blogWrite(@AuthUser UserVO userVO, PostVO postVO) {
		
		
		return "redirect:/blog";
		
	}

}
