package com.gdu.app01.xml01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass2 {

	public static void main(String[] args) {

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml01/app-context2.xml");
		
		Student student = ctx.getBean("student", Student.class);
		
		System.out.println(student.getStuNo());
		System.out.println(student.getName());
		student.getCalculator().add(2, 3);
		student.getCalculator().mul(3, 4);
		
		
	}

}
