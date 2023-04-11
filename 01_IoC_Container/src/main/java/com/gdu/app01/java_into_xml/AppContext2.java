package com.gdu.app01.java_into_xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext2 {

	@Bean
	public Publisher publisher2() {
		Publisher publisher = new Publisher();
		publisher.setName("고려출판사");
		publisher.setTel("032-066-1345");
		return publisher;
	}
	@Bean
	public Book book2() {
		Book book = new Book();
		book.setTitle("자바");
		book.setPublisher(publisher2());
		return book;
	}
}
