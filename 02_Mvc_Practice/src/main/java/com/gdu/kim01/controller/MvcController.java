package com.gdu.kim01.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.kim01.domain.Person;

@Controller
public class MvcController {
	
	@GetMapping("/detail.do")
	public String detail(HttpServletRequest request, Model model) {
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("name"));
		String name = opt1.orElse("동길홍");
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("age"));
		int age = Integer.parseInt(opt2.orElse("0"));
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "mvc/detail";
	}
	
	@GetMapping("/detail.me")
	public String detailMe(@RequestParam(value="name", required=false, defaultValue="녹두") String name,
							@RequestParam(value="age", required=false, defaultValue="666666") int age,
							Model model) {
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "mvc/detail";
	}
	
	@GetMapping("/detail.gdu")
	public String detailGdu(Person p) {
		return "mvc/detail";
	}
	
}
