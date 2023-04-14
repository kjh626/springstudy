package com.gdu.kim03.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.kim03.domain.Person;

public class FirstServiceImpl implements IFirstService {

	@Override
	public Person execute1(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String strMonth_day = request.getParameter("month_day");
			strMonth_day =  strMonth_day.isEmpty() ? "0" : strMonth_day;
			int month_day = Integer.parseInt(strMonth_day);
			
			String strBirth = request.getParameter("birth");
			strBirth =  strBirth.isEmpty() ? "0" : strBirth;
			int birth = Integer.parseInt(strBirth);
		
			return new Person(month_day, birth);
			
		} catch(Exception e) {
			try {
				response.setContentType("text/plain; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println(e.getMessage());  // $.ajax의 error로 넘기는 예외 메시지
				out.flush();
				out.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
			return null;
		}
		
	}

}
