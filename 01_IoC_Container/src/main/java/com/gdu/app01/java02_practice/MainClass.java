package com.gdu.app01.java02_practice;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext("com.gdu.app01.java02_practice");
		
		Student student = ctx.getBean("student", Student.class);
		
		System.out.println("점수: " + student.getScores());
		System.out.println("연락처: " + student.getContact());
		System.out.println("상: " + student.getAwards());
		ctx.close();
	}

}
