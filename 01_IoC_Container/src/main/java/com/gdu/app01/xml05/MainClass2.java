package com.gdu.app01.xml05;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass2 {

	public static void main(String[] args) {

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml05/app-context2.xml");
		
		Person person = ctx.getBean("person", Person.class);
		
		List<String> hobbies = person.getHobbies();
		for(int i = 0; i < hobbies.size(); i++) {
			System.out.println("취미" +  (i + 1) + ": " + hobbies.get(i));
		}
		
		for(String contact : person.getContacts()) {
			System.out.println("연락처: " + contact);
		}
		
		for(Entry<String, String> friend : person.getFriends().entrySet()) {
			System.out.println(friend.getKey() + ":" + friend.getValue());
		}
		
		
	}

}
