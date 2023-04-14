package com.gdu.kim03.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.kim03.domain.Person;

public interface IFirstService {
	public Person execute1(HttpServletRequest request, HttpServletResponse response);
}
