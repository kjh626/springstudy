package com.gdu.app01.java01_practice;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainClass {

	public static void main(String[] args) {

		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
		
		Friend friend1 = ctx.getBean("friend", Friend.class);
		System.out.println(friend1.getName());
		System.out.println(friend1.getGender());
		System.out.println(friend1.getAddr().getAddress());
		System.out.println(friend1.getAddr().getTel());
		
		Friend friend2 = ctx.getBean("friend2", Friend.class);
		System.out.println(friend2.getName());
		System.out.println(friend2.getGender());
		System.out.println(friend2.getAddr().getAddress());
		System.out.println(friend2.getAddr().getTel());
	}

}
