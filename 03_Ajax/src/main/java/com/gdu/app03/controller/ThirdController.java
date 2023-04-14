package com.gdu.app03.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gdu.app03.domain.Contact;
import com.gdu.app03.service.IThirdService;

@Controller
public class ThirdController {

	
	// field
	// 현재 필드는 널값 스프링컨테이너에 등록된 빈 주입해주려면?? -> @Autowired 주입
	// 스프링컨테이너에 있는 빈을 불러들이려면? @Autowired 방법 3가지 - field, constructor, setter method
	// ThirdController 에서는 setter method 연습해볼 것! 
	private IThirdService thirdService; // 이 이름 빈의 id랑 맞춰라
	
	// setter method
	@Autowired    // setter method 형식의 자동 주입의 경우 @Autowired를 생략할 수 없다.
	public void method(IThirdService thirdService) {   // Spring Container에서 IThirdService 타입의 Bean을 찾아서 매개변수에 주입한다.
		this.thirdService = thirdService;
	}
	
	// @ResponseBody 적어줄 필요 없다. ResponseEntity의 내장 기능으로 응답 본문에 데이터를 실어주는 기능이 들어있다.
	// 오타내지마라 application/json 자신없으면 MediaType 써라
	@PostMapping(value="/third/ajax1", produces="application/json")           // 파라미터 없이 데이터를 주고 받는 방법. (고도화된 방법) => 오직 이 방법으로만 쓸 수 있다. @RequestBody
	public ResponseEntity<Contact> ajax1(@RequestBody Contact contact){      // 요청 본문(RequestBody)에 포함된 JSON 데이터를 Contact contact 객체에 저장해 주세요. <- 여기서 Jackson이 JSON 데이터를 Contact 객체로 변환시켜준다.
		 return thirdService.execute1(contact);
	}
	
	@PostMapping(value="/third/ajax2", produces="application/json")
	public ResponseEntity<Map<String,String>> execute2(@RequestBody Map<String, String> map){
		return thirdService.execute2(map);
	}
	
}
