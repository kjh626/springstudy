package com.gdu.app01.xml06;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass2 {

	public static void main(String[] args) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml06/app-context2.xml");
		GymMember member = ctx.getBean("member", GymMember.class);
		member.memberInfo();
		ctx.close();
	}

}
