package com.gdu.app01.java02_practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {
	
	@Bean(name="student")
	public Student aaa() {
		
		List<Integer> scores = new ArrayList<Integer>();
		for(int cnt=0; cnt < 5; cnt++) {
			scores.add( (int)(Math.random() * 101) );
		}
		
		Set<String> awards = new HashSet<String>();
		awards.add("금상");
		awards.add("은상");
		awards.add("대상");
		
		Map<String, String> contact = new HashMap<String, String>();
		contact.put("address", "jeju");
		contact.put("tel", "000-000-0000");
		
		Student student = new Student();
		student.setScores(scores);
		student.setAwards(awards);
		student.setContact(contact);
		return student;
	}

}
