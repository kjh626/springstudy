package com.gdu.app07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gdu.app07.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	
}
