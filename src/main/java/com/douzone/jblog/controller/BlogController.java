package com.douzone.jblog.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.UserVO;




@Controller
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@GetMapping("")
	public String index(@AuthUser UserVO vo, Model model) {
		
		BlogVO blog = blogService.getBlog(vo);
		model.addAttribute("blog", blog);
		
//		System.out.println("blogController - index" +vo);
		return "blog/blog-main";
	}
	
	@RequestMapping("/adminBasic")
	public String blogAdmin(@AuthUser UserVO vo, Model model) {
		

		BlogVO blog = blogService.getBlog(vo);
		model.addAttribute("blog", blog);
		
//		System.out.println("blogController - main" + blog);
		
		return "blog/blog-admin-basic";
	}



	@PostMapping("/adminBasic")
	public String blogAdmin(BlogVO vo, @RequestParam("file") MultipartFile file) {

		String logo = fileUploadService.restoreImage(file);
		vo.setLogo(logo);
		blogService.update(vo);
		servletContext.setAttribute("blog", vo);
		
		System.out.println("blogAdmin - blogAdmin" +vo);

		
		
		return "redirect:/blog/blog-admin-basic";
	}
	
	

	
	
	
}
