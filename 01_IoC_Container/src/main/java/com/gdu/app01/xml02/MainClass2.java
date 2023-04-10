package com.gdu.app01.xml02;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass2 {

	public static void main(String[] args) {
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml02/app-context2.xml");
		
		Academy academy = ctx.getBean("academy", Academy.class);
		
		System.out.println("이름: " + academy.getName());
		System.out.println("도로명: " + academy.getAddress().getRoadAddress());
		System.out.println("지번: " + academy.getAddress().getJibunAddress());
		System.out.println("TEL: " + academy.getAddress().getContact().getTel());
		System.out.println("FAX: " + academy.getAddress().getContact().getFax());
	}
	
}
