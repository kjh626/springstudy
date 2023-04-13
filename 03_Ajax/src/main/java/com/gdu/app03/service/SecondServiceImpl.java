package com.gdu.app03.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.app03.domain.BmiVO;

public class SecondServiceImpl implements ISecondService {

	@Override
	public BmiVO execute1(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			double weight = Double.parseDouble(request.getParameter("weight"));
			double height = Double.parseDouble(request.getParameter("height")) / 100;

			double bmi = weight / (height * height);
			
			if(bmi < 0) {
				throw new RuntimeException(bmi + "bmi가 이상해~");
			}
			
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
			
			// weight 랑 height는 0 보내도 상관 없다. bmi랑 obesity만 필요해서
			return new BmiVO(weight, height, bmi, obesity);
			
		} catch(Exception e) {
			// 정확하게 exception 찾으면 numberformatException
			try {
				response.setContentType("text/plain; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("몸무게와 키 입력을 확인하세요");  // $.ajax의 error로 넘기는 예외 메시지
				out.flush();
				out.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
			return null;
		}
		
	}

	@Override
	public Map<String, Object> execute2(BmiVO bmiVO) {
		
		try {
			/*
			if((bmiVO.getWeight() + "").isEmpty() || (bmiVO.getHeight()+"").isEmpty()) {
				throw new NumberFormatException("공백이라고");
			}
			*/
			
			double weight = bmiVO.getWeight();
			double height = bmiVO.getHeight() / 100;
			
			double bmi = weight / (height * height);
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
			
			/*
			if(bmi < 0) {
				throw new RuntimeException("bmi 수치가 0 미만입니다.");
			}
			*/
			
			// 반환을 위한 맵
			Map<String, Object> map = new HashMap<String, Object>();
			
			// 맵에 저장 2가지면 된다.
			map.put("bmi", bmi);
			map.put("obesity", obesity);
			
			return map;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		/*
		 * catch(NumberFormatException e) {
			System.out.println("넘버익셉션" + e.getMessage());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("exception", e.getMessage());
			return map;
		} catch(RuntimeException e) {
			System.out.println("런타임" + e.getMessage());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("exception", e.getMessage());
			return map;
		}
		*/
	} 

}
