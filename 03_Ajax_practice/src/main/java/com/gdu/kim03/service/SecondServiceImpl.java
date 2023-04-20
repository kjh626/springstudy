package com.gdu.kim03.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.gdu.kim03.domain.BmiVO;

public class SecondServiceImpl implements ISecondService {
	
	
	@Override
	public ResponseEntity<BmiVO> execute1(HttpServletRequest request) {

		try {
			double weight = Double.parseDouble(request.getParameter("weight"));
			double height = Double.parseDouble(request.getParameter("height")) / 100;
			
			double bmi = weight / (height * height);
			
			String obesity = null;
			if(bmi < 18.5) {
				obesity = "저체중";
			} else if(bmi < 24.9) {
				obesity = "정상";
			} else if(bmi < 29.9) {
				obesity = "과체중";
			} else {
				obesity = "비만";
			}
			return new ResponseEntity<BmiVO>(new BmiVO(weight, height, bmi, obesity), HttpStatus.OK);
		} catch(Exception e) {
			BmiVO bmiVO = null;
			return new ResponseEntity<BmiVO>(bmiVO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> execute2(BmiVO bmiVO) {

		double weight = bmiVO.getWeight();
		double height = bmiVO.getHeight();
		
		if(weight == 0 || height == 0) {
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);    // 응답 코드가 정상(200)이 아니므로 $.ajax에 error로 전달
		}
		
		double bmi = weight / (height * height / 10000);
		String obesity = null;
		if(bmi < 18.5) {
			obesity = "저체중";
		} else if(bmi < 24.9) {
			obesity = "정상";
		} else if(bmi < 29.9) {
			obesity = "과체중";
		} else if(bmi > 30){
			obesity = "비만";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bmi", bmi);
		map.put("obesity", obesity);
		
		MultiValueMap<String, String> header = new HttpHeaders();
		header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		return new ResponseEntity<Map<String,Object>>(map, header, HttpStatus.OK);
	}
}
