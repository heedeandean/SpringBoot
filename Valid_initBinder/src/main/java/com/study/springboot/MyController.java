package com.study.springboot;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "Valid_initBinder";
	}

	@RequestMapping("/insertForm")
	public String insert1() {
		return "createPage";
	}

	@RequestMapping("/create")
	public String insert2(@ModelAttribute("dto") @Valid ContentDto contentDto, BindingResult result) {
		String page = "createDonePage";
		System.out.println(contentDto);

//	    강한 결합 
//		ContentValidator validator = new ContentValidator();
//		validator.validate(contentDto, result);

		if (result.hasErrors()) {

			if (result.getFieldError("writer") != null) {
				System.out.println("1:" + result.getFieldError("writer").getCode());
			}
			if (result.getFieldError("content") != null) {
				System.out.println("2:" + result.getFieldError("content").getCode());
			}
			page = "createPage";
		}

		return page;
	}

//	약한 결합
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ContentValidator());
	}
}
