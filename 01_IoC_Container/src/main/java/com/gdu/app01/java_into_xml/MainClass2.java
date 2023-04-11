package com.gdu.app01.java_into_xml;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass2 {

	public static void main(String[] args) {

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("java_into_xml/app-context2.xml");
		
		Book book = ctx.getBean("book2", Book.class);
		
		System.out.println(book.getTitle());
		System.out.println(book.getPublisher().getName());
		System.out.println(book.getPublisher().getTel());
		
	}

}
