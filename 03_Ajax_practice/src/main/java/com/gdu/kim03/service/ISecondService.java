package com.gdu.kim03.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.gdu.kim03.domain.BmiVO;

public interface ISecondService {
	public ResponseEntity<BmiVO> execute1(HttpServletRequest request);
	public ResponseEntity<Map<String, Object>> execute2(BmiVO bmiVO);
}
