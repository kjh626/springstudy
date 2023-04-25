package com.gdu.app09.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app09.service.EmployeeListService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeListService employeeListService;
	
	@GetMapping("/employees/pagination.do")
	public String pagination(HttpServletRequest request, Model model) {
		employeeListService.getEmployeeListUsingPagination(request, model);
		return "employees/pagination";
	}
	
	// 세션쓰고싶으면 세션으로 매개변수 받으면 됨, @RequestParm 생략 => request 같이 들어가도 된다. 그래도 일단 적어놔라
	@GetMapping("/employees/change/record.do")
	public String changeRecord(HttpSession session
							  , HttpServletRequest request
			                  , @RequestParam(value="recordPerPage", required=false, defaultValue="10") int recordPerPage) {
	session.setAttribute("recordPerPage", recordPerPage);
	return "redirect:/employees/pagination.do";    // 수정 필요
		
	}
	
}
