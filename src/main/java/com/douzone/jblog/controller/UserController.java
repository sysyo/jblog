package com.douzone.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	// joinform
//	@RequestMapping(value="/join", method=RequestMethod.GET)
	@GetMapping("/join") // jsp page -> get으로 받아올 때 들고올 거 없을 때
	public String join(@ModelAttribute UserVO vo) {

		return "user/join";
	}

	@PostMapping("/join")
	public String join(@ModelAttribute @Valid UserVO vo, BindingResult result, Model model) {

		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		userService.join(vo);

		return "redirect:/user/joinsuccess";
	}

	@GetMapping("/joinsuccess")
	public String joinsuccess() {

		return "user/joinsuccess";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "user/login";
	}

}
