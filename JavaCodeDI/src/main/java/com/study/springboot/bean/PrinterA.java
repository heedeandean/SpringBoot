package com.study.springboot.bean;

public class PrinterA implements Printer {

	@Override
	public void print(String msg) {
		System.out.println(msg);
	}

}