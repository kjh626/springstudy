package com.gdu.kim03.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.kim03.domain.Person;
import com.gdu.kim03.service.IFirstService;

@Controller
public class FirstController {

	@Autowired
	private IFirstService firstService;
	
	@GetMapping(value="/first/ajax1", produces="application/json")
	public Person execute1(HttpServletRequest request, HttpServletResponse response) {
		return firstService.execute1(request, response);
	}
	
}
