package com.gdu.app03.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app03.domain.Person;
import com.gdu.app03.service.IFirstService;

@Controller
public class FirstController {

	// get방식의 요청.
	// 반환타입에 대한 고민, 파라미터에 대한 고민
	
	//     FirstServiceImpl을 Spring Container에 Bean으로 등록시켜보자. (root-context.xml에 <bean>태그 등록)
	// Spring Container에 등록된 firstService Bean을 아래 필드 firstService에 주입해보자. (필드에 @Autowired 추가)
	// 3가지 방법이 있다.
	
	@Autowired
	private IFirstService firstService;
	
	
	@ResponseBody    // 메소드의 반환 값이 Jsp이름이 아니다. 응답하는 값(데이터)이다.(요청한 곳으로 보내는 데이터이다.) => 값을 응답해주세요! ajax처리할 때 쓰는 애너테이션 
	@GetMapping(value="/first/ajax1", produces="application/json")   // produces : 응답할 데이터의 MIME TYPE
	public Person ajax1(HttpServletRequest request) {                // "Jackson 라이브러리"가 반환값 Person 객체를 자동으로 JSON 데이터로 변환한다. (응답할 데이터타입 json으로 적어줬기 때문에 잭슨이 알아서 json으로 바꿔준다)
		return firstService.execute1(request);
		// first.jsp에서의 요청 파라미터 FirstController의 request에 전달 → request가 FirstServiceImpl의 execute1 메소드에 전달
	}
	
	@ResponseBody
	@GetMapping(value="/first/ajax2", produces="application/json")
	public Map<String, Object> ajax2(@RequestParam("name") String name, @RequestParam("age") int age) {  // 인터페이스에도 매개변수로 name, age 받으니까 request보다는 이거 쓰는 게 좋아보임
		// int age 에서 빈 문자열을 정수로 바꿔주려는 과정에서 오류 발생. name 상관 없음 String이라.
		return firstService.execute2(name, age);        // "Jackson 라이브러리"가 반환값 Map을 자동으로 JSON 데이터로 변환한다.
	}
	
	
}
