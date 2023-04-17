package com.gdu.kim04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.kim04.service.BoardService;

@RequestMapping("/board")
@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list.do")
	public String list(Model model) {
		System.out.println(boardService.getBoardList());
		return "board/list";
	}
	
	@GetMapping("detail.do")
	public String detail(Model model) {
		System.out.println(boardService.getBoardByNo(2));
		return "board/detail";
	}
}
