package com.gdu.app09.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface EmployeeListService {
	// 직접 이동 시키는 방식으로
	public void getEmployeeListUsingPagination(HttpServletRequest request, Model model);
}