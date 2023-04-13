package com.gdu.app03.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.app03.domain.BmiVO;

public interface ISecondService {
	
	// 반환 타입 1 객체, 2 맵 -> 둘 다 잭슨이 제이슨으로 바꿔주는 애들이다.
	// response 매개변수로 받는 이유 => 에러 났을 때 값을 화면에 띄우기 위해서 (연습하려고)
	public BmiVO execute1(HttpServletRequest request, HttpServletResponse response);
	public Map<String, Object> execute2(BmiVO bmiVO);
	
	// @requestParam으로 받는 것은 연습 안 함.
	
	
}
