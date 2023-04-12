package com.gdu.kim01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.kim01.domain.Bbs;

@Controller
public class DiController {
	private Bbs bbs1;
	private Bbs bbs2;
	
	public DiController(Bbs bbs1, Bbs bbs2) {
		super();
		this.bbs1 = bbs1;
		this.bbs2 = bbs2;
	}
	
	@GetMapping("/bbs/detail.do")
	public String detail(Model model) {
		
		model.addAttribute("bbs1", bbs1);
		model.addAttribute("bbs2", bbs2);
		
		return "bbs/detail";
	}
}
