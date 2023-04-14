package com.gdu.app03.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.gdu.app03.domain.BmiVO;

public class SecondServiceImpl implements ISecondService {
	
	/*
		ResponseEntity<T> 클래스
		1. Ajax 응답 데이터를 생성하는 클래스
		2. 생성자 중 하나의 사용법
			public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) 
			1) @Nullable T body                                : 실제로 응답할 데이터
			2) @Nullable MultiValueMap<String, String> headers : 응답 헤더(대표적으로 Content-Type)
				                                                    → produces=MediaType.APPLICATION_JSON_VALUE 이거 빼버리고 응답헤더로 쓴다고..
			3) HttpStauts status                               : 응답 코드(200, 404, 500 등)  -> 우리가 응답데이터에 404, 500 으로 처리해서 보내면 try-catch없어도 ajax의 error쪽으로 보낸다. 200은 success
	*/
	@Override
	public ResponseEntity<BmiVO> execute1(HttpServletRequest request) {

		try {
			double weight = Double.parseDouble(request.getParameter("weight"));
			double height = Double.parseDouble(request.getParameter("height")) / 100;
	
			double bmi = weight / (height * height);    // bmi = 몸무게 / 키(m) * 키(m)
			
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
			
			// 스프링프레임워크에 있는 HttpStauts , HttpStatus.OK == 200
			return new ResponseEntity<BmiVO>(new BmiVO(weight, height, bmi, obesity), HttpStatus.OK);
			
		} catch(Exception e) {
			
			BmiVO bmiVO = null;
			// 직접적인 null 말고 BmiVO로 반환타입 맞춰줘라 (아래 괄호에 직접 null 적지 마라)
			return new ResponseEntity<BmiVO>(bmiVO, HttpStatus.INTERNAL_SERVER_ERROR);     // HttpStatus가 500이므로 알아서 $.ajax의 error에서 처리된다.
			// INTERNAL_SERVER_ERROR: 500
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
		
		// 실제로 응답할 데이터
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bmi", bmi);
		map.put("obesity", obesity);
		
		// 응답 헤더(Content-Type)  -  @GetMapping produces 대신 사용
		// HttpHeaders의 인터페이스 MultiValueMap
		MultiValueMap<String, String> header = new HttpHeaders();
		header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		return new ResponseEntity<Map<String,Object>>(map, header, HttpStatus.OK);
		
	}
	
	
	
	
	
	/*
	@Override
	public BmiVO execute1(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			double weight = Double.parseDouble(request.getParameter("weight"));
			double height = Double.parseDouble(request.getParameter("height")) / 100;

			double bmi = weight / (height * height);  // bmi = 몸무게 / 키(m) * 키(m)
			
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
			return new BmiVO(weight, height, bmi, obesity);		// $.ajax의 success로 넘기는 값
			// Bean의 형태로 반환, ajax의 success로 넘어간다.
			
		} catch(Exception e) {
			// 예외가 발생하는 상황 : 입력된 몸무게와 키가 double로 제대로 변환이 되지 않았을 때
			// 정확하게 exception 찾으면 NumberFormatException
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
			
			//if((bmiVO.getWeight() + "").isEmpty() || (bmiVO.getHeight()+"").isEmpty()) {
			//	throw new NumberFormatException("공백이라고");
			//}
			
			
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
			
			
			//if(bmi < 0) {
			//	throw new RuntimeException("bmi 수치가 0 미만입니다.");
			//}
			
			
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
		
		// catch(NumberFormatException e) {
		//	System.out.println("넘버익셉션" + e.getMessage());
		//	Map<String, Object> map = new HashMap<String, Object>();
		//	map.put("exception", e.getMessage());
		//	return map;
		//} catch(RuntimeException e) {
		//	System.out.println("런타임" + e.getMessage());
		//	Map<String, Object> map = new HashMap<String, Object>();
		//	map.put("exception", e.getMessage());
		//	return map;
		}
		
	} 
	 */
	
}
