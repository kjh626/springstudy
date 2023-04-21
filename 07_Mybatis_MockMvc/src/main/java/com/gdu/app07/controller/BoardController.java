package com.gdu.app07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gdu.app07.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	// 평가처럼 jsp로 받는 파라미터를 Controller에서는 (3가지 방법 중) request로 전부 받을 것이다. => request에서 알아서 꺼내서 쓰면 된다. 모든 파라미터 작업을 서비스가 할 것이다.
}
