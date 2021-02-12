package com.study.springboot.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean // Spring이 IoC 방식으로 관리하는 객체
	public Member member1() {
		Member m1 = new Member();

		// Setter Injection
		m1.setName("엄홍식");
		m1.setNickname("유아인");
		m1.setPrinter(new PrinterA());

		return m1;
	}

	@Bean(name = "hello")
	public Member member2() {

		// Constructor Injection
		return new Member("엄희진", "희딘딘", new PrinterA());
	}

	@Bean
	public PrinterA printerA() {
		return new PrinterA();
	}

	@Bean
	public PrinterB printerB() {
		return new PrinterB();
	}

}