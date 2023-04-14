package com.gdu.app03.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app03.domain.BmiVO;
import com.gdu.app03.service.ISecondService;

@Controller
public class SecondController {

	
	private ISecondService secondService;   // 필드이름을 bean의 아이디랑 같게 쓰는 게 좋다. 빈의 아이디로 등록한 거 그대로 맞춰서 써줘라
	
	// 생성자로 빈 가져오기 : @Autowired로 생성자로 빈가져오기 == 롬복 @AllargsConstructor 애너테이션이랑 똑같넹..? 롬복으로 대체해도 된다.
	// @AllargsConstructor으로 아래 생성자를 대체해도 좋다!!
	@Autowired  // 생성자에서 @Autowired는 생략할 수 있다.
	public SecondController(ISecondService secondService) {   // 컨테이너에 담긴 빈(firstService, secondService)이 이 매개변수로 주입된다.(secondService를 찾아 주입)
		super();
		this.secondService = secondService;   // 이렇게 빈이 매개변수를 거쳐서 필드에 주입된다.
	}
	
	@ResponseBody
	@GetMapping(value="/second/bmi1", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BmiVO> bmi1(HttpServletRequest request) {
		return secondService.execute1(request);
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	// get방식의 요청이니 getMapping
	@ResponseBody  // 이걸 붙여줘야만 리턴하는게 응답데이터로 넘어간다(.jsp 가 아니라)
	@GetMapping(value="/second/bmi1", produces=MediaType.APPLICATION_JSON_VALUE)  // MediaType.APPLICATION_JSON_VALUE는 "application/json"이다
	public BmiVO bmi1(HttpServletRequest request, HttpServletResponse response) {
		return secondService.execute1(request, response);   // 반환할 게 bean이네?(BmiVO) + 반환하는 데이터의 타입을 APPLICATION_JSON으로 잡아둠 -> jackson이 json데이터로 변환해서 전달해준다.
	}

	// 파라미터 커맨드 객체로 받기
	@ResponseBody
	@GetMapping(value="/second/bmi2", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> bmi2(BmiVO bmiVO){
		return secondService.execute2(bmiVO);
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
}
