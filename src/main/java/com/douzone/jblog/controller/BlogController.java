package com.douzone.jblog.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.UserVO;




@Controller
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
//	@Autowired
//	private FileUploadService fileUploadService;
	
	@GetMapping("")
	public String index(@AuthUser UserVO vo, Model model) {
		
		BlogVO blog = blogService.getBlog(vo);
		model.addAttribute("blog", blog);
		
		System.out.println("blogController - index" +vo);
		return "blog/blog-main";
	}
	



//	@GetMapping("/adminBasic")
//	public String blogAdmin(BlogVO blog, @RequestParam("file") MultipartFile file) {
//		
//		try {
//		String logo = fileUploadService.restoreImage(file);
//		blog.setLogo(logo);
//		} catch(FileUploadException ex) {
//			LOGGER.info("adminBasic" + ex);
//		}
//		
//		
//		
//		return "blog/blog-admin-basic";
//		return "redirect:/adminBasic";
//	}
	
	

	
	
	
}
