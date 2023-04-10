package com.gdu.app01.xml02;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		// src/main/resources/xml02 디렉터리에 있는 app-context.xml 파일에 정의된 Bean을 쓸게요! 이 코드는 외울 필요는 없다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml02/app-context.xml");   // src/main/resources는 명시하지 않습니다.
		
		// Bean 중에서 academy란 id를 가진 Bean을 주세요!
		Academy academy = ctx.getBean("academy", Academy.class);   // (Academy)ctx.getBean("student")
		
		System.out.println("이름: " + academy.getName());
		System.out.println("도로명주소: " + academy.getAddress().getRoadAddress());
		System.out.println("지번주소: " + academy.getAddress().getJibunAddress());
		System.out.println("전화번호: " + academy.getAddress().getContact().getTel());
		System.out.println("팩스번호: " + academy.getAddress().getContact().getFax());
		
		ctx.close();
	}

}
