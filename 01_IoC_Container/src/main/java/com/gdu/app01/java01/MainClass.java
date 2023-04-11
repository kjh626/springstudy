package com.gdu.app01.java01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		/*
			@Configuration, @Bean 애너테이션으로 생성한 Bean을 가져올 때 사용하는 스프링 클래스
			AnnotationConfigApplicationCntext

		*/
		/* 
		   @Configuration 붙여놓은 파일이 하나다 = ()안에 클래스 이름 적어주는 방법 쓸 수 있고,
		   @Configuration 붙여놓은 파일이 2개 이상이다 = 한 패키지에 모아놓고 패키지의 이름을 적어주는 방법이 있다.
		*/ 
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);           // AppContext.java 파일에 있는 Bean을 주세요!
		// AbstractApplicationContext ctx = new AnnotationConfigApplicationContext("com.gdu.app01.java01");  // com.gdu.app01.java01 패키지에 있는 모든 Bean을 주세요! (패키지 2개 이상은 중괄호({}) 써서 사용 가능)
		User user1 = ctx.getBean("user1", User.class);
		System.out.println(user1.getId());
		System.out.println(user1.getContact().getTel());
		System.out.println(user1.getContact().getFax());
		
		User user2 = ctx.getBean("user2", User.class);
		System.out.println(user2.getId());
		System.out.println(user2.getContact().getTel());
		System.out.println(user2.getContact().getFax());
		
		ctx.close();
		
	}

}
