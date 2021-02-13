package com.study.springboot.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	@Autowired
	Member m1;
	@Autowired
	@Qualifier("printerB")
	Printer printer;
	@Autowired
	Member m2;
	
	@RequestMapping("/")
	public @ResponseBody String root() {
		m1.print();
		m1.setPrinter(printer);
		m1.print();
		
		if(m1 == m2) {
			System.out.println("동일한 객체");
		} else {
			System.out.println("서로 다른 객체");
		}
		return "Annotation 사용하기";
	}
}
