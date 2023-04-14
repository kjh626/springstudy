package com.gdu.app03.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.gdu.app03.domain.BmiVO;

public interface ISecondService {
	
	public ResponseEntity<BmiVO> execute1(HttpServletRequest request);
	public ResponseEntity<Map<String, Object>> execute2(BmiVO bmiVO);
	
	
	// 반환 타입 1 객체(Bean), 2 맵(Map) -> 둘 다 잭슨이 제이슨으로 바꿔주는 애들이다.
	// response 매개변수로 받는 이유 => 에러 났을 때 값을 화면에 띄우기 위해서 (연습하려고)
	// public BmiVO execute1(HttpServletRequest request, HttpServletResponse response);
	// public Map<String, Object> execute2(BmiVO bmiVO);
	
	// @requestParam으로 받는 것은 연습 안 함.
	
	
	// 응답 개체. 스프링에는 ajax전용 응답 개체가 만들어져 있다(스프링 클래스) : ResponseEntity<T>
}
