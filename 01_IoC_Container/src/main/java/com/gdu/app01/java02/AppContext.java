package com.gdu.app01.java02;

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
	
	@Bean 
	public Student stu(){    // <bean id="stu" class="Student">
		
		// 0~100 난수 5개 <- xml로 만들기 난감.. 자바로 하면 좋겠지..?
		List<Integer> scores = new ArrayList<Integer>();
		for(int cnt = 0; cnt < 5; cnt++) {            // 5번 반복합니다.
			scores.add( (int)(Math.random() * 101) ); // 0부터 101개의 난수가 발생된다. (0~100)
		}
		
		// 상 3개
		Set<String> awards = new HashSet<String>();
		awards.add("개근상");
		awards.add("우수상");
		awards.add("장려상");

		// address, tel
		Map<String, String> contact = new HashMap<String, String>();
		contact.put("address", "incheon");	
		contact.put("tel", "032-1111-1111");

		// Bean 생성 및 반환
		Student student = new Student();
		student.setScores(scores);
		student.setAwards(awards);
		student.setContact(contact);
		return student;
	}
	
}
