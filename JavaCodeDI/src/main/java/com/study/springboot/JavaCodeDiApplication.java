package com.study.springboot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.study.springboot.bean.Config;
import com.study.springboot.bean.Member;
import com.study.springboot.bean.Printer;

// @SpringBootApplication
public class JavaCodeDiApplication {

	public static void main(String[] args) {
		// SpringApplication.run(JavaCodeDiApplication.class, args);
		
		// IoC 컨테이너 생성
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		// Bean 가져오기
		Member m1 = (Member)context.getBean("member1");
		m1.print();
		
		Member m2 = context.getBean("hello", Member.class);
		m2.print();
		
		Printer printer = context.getBean("printerB", Printer.class);
		m1.setPrinter(printer);
		m1.print();
		
		// 싱글톤인지 확인
		if(m1 == m2) {
			System.out.println("동일한 객체");
		} else {
			System.out.println("서로 다른 객체");
		}
	}

}
