package com.study.springboot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "Form 데이터 전달 & 사용";
	}
	
	@RequestMapping("/test1")
	public String test1(HttpServletRequest httpServletRequest, Model model) {
		
		String id = httpServletRequest.getParameter("id");
		String name = httpServletRequest.getParameter("name");
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		
		return "test1";
	}
	
	@RequestMapping("/test2")
	public String test2(@RequestParam("id") String id
			 		  , @RequestParam("name") String name
			 		  , Model model) 
	{
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		
		return "test2";
	}

	@RequestMapping("/test3")
	public String test3(Member member, Model model)
	{	
		return "test3";
	}

	@RequestMapping("/test4/{stuId}/{name}")
	public String getStudent(@PathVariable String stuId
						   , @PathVariable String name
						   , Model model)
	{	
		model.addAttribute("id", stuId);
		model.addAttribute("name", name);
		
		return "test4";
	}
	
	
	
	
}
