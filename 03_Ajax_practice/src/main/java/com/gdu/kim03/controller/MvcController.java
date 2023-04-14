package com.gdu.kim03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/first.prac")
	public String first() {
		return "first";
	}
}
