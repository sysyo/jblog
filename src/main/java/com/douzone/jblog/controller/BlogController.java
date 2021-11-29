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
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.UserVO;
import com.douzone.jblog.vo.PostVO;

@Controller
@RequestMapping("/blog/{id}")
public class BlogController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private BlogService blogService;

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;

	@RequestMapping({"","/{no}"})
	public String index(@PathVariable(value = "no", required = false) Long no,
						@PathVariable("id") String id, 
						Model model) {

		BlogVO blog = blogService.getBlog(id);
		List<CategoryVO> list = categoryService.getCategory(id);
		model.addAttribute("blog", blog);		
		model.addAttribute("list",list);
		
		return "blog/blog-main";
	}

	// 블로그 관리 페이지 넘어가기
	@RequestMapping("/adminBasic")
	public String blogAdmin(@AuthUser UserVO vo, Model model) {

		BlogVO blog = blogService.getBlog(vo.getId());
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
	public String adminCategory(@AuthUser UserVO vo, Model model, BlogVO bvo) {
		
//		System.out.println(vo);
		BlogVO blog = blogService.getBlog(vo.getId());
//		System.out.println("vo임 " + vo);
//		System.out.println("blog임 " + blog);
		model.addAttribute("blog", blog);
		
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
	
	
	// 글 작성 페이지로 이동, 카테고리 나타나게 하기
	@GetMapping("/write")
	public String blogWrite(@AuthUser UserVO vo, Model model) {
		
		List<CategoryVO> list = categoryService.getCategory(vo.getId());
		model.addAttribute("list", list);
		
//		System.out.println(list);
		return "blog/blog-admin-write";
	}
	
	// 글 작성
	@PostMapping("/write")
	public String blogWrite(
			@AuthUser UserVO userVO, 
			PostVO postVO, 
			@RequestParam(value="category") int no) {
		postVO.setCategoryNo(no);
		postService.writePost(postVO);
		
		System.out.println("postvo임" + postVO );
		System.out.println("유저  + " + userVO);
		
		String id = userVO.getId();
		System.out.println(id);
		

		// @RequestParam으로 jsp 페이지에 있는
		// <select	name="category"> 의 option value 값에 no 를 들고와서 categoryNo를 postVO에 넣어주기
		// -> int 값으로 맞춰줄 수 있음
		
		return "redirect:/blog/" + id;
		
	}

}

// model -> jsp 쓸 거 

// @RequestParam(value="category", required=true, defaultValue= "1") int no) {
// 		- value는 jsp 에서 name 값
// 		- @RequestParam의 required의 기본값은 true 


// public String index(@AuthUser UserVO vo, Model model) {
// -> 이렇게 하면 Auth 있는 User만 블로그 들어갈 수 있기 때문에 
//    @PathVariable 사용해서 id 값으로 주소만 입력하면 그 블로그로 들어갈 수 있도록