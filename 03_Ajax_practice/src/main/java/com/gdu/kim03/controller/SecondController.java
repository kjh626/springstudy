package com.gdu.kim03.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.kim03.domain.BmiVO;
import com.gdu.kim03.service.ISecondService;

@Controller
public class SecondController {

	private ISecondService secondService;
	
	@Autowired 
	public void secondService(ISecondService secondService) {
		this.secondService = secondService;
	}
	
	@GetMapping(value="/second/bmi1", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BmiVO> bmi1(HttpServletRequest request) {
		return secondService.execute1(request);
	}
	
	@GetMapping("second/bmi2")
	public ResponseEntity<Map<String, Object>> bmi2(BmiVO bmiVO){
		return secondService.execute2(bmiVO);
	}
	
}
