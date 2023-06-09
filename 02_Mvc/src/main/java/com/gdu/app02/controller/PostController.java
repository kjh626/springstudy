package com.gdu.app02.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {
	// 우리가 하려는 것은 redirect 
	
	// 문제는 파라미터 2개를 어떻게 처리할 것이냐? 3가지 방법 리뷰

	// ① 리퀘스트, ② @requestParam, ③ 커맨드 객체
	/*	
		public String detail(HttpServletRequest request) { }
		public String detail(@RequestParam("name") String name, @RequestParam("age") int age) { }
		public String detail(Person p) {
			return "post/detail";
		}
	*/
	@GetMapping("/post/detail.do")
	public String detail(HttpServletRequest request) throws Exception {   // name, age 파라미터가 있다.  // 예외 던진거 URLEncoder때문
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		System.out.println("/post/detail.do");;
		System.out.println("name: " + name + ", age: " + age);
		
		// return "redirect:이동경로";    ★ 중요한 것은 redirect를 할 때는 이동경로를 ★Mapping★으로 적어줘야 한다.
		return "redirect:/post/list.do?name=" + URLEncoder.encode(name, "UTF-8") + "&age=" + age;    // /post/list.do 매핑으로 이동하시오! name, age 파라미터를 다시 붙인다.

		// redirect할 때 값을 전달해주고 싶으면 이동경로 뒤에 파라미터를 꼭 붙여줘라(필요하면 파라미터를 다시 갖다 붙여줘야한다.)
	}
	
	// 파라미터값 전달 안 된다.(이동경로에서 파라미터 안 붙여줬으니까..) ⇔ (전달은 forward)
	@GetMapping("/post/list.do")
	public String list(HttpServletRequest request,   // name, age 파라미터가 있다.
						Model model) {
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		// forward할 때 이름하고 나이를 실어서 보내려면 -> 스프링에서는 Model이 필요하다.
		model.addAttribute("name", name);
		model.addAttribute("age", age);

		//  /WEB-INF/views/post/list.jsp로 forward하겠다!
		return "post/list";
	}

	@GetMapping("/post/detail.me")
	public String detailMe(HttpServletRequest request,
							RedirectAttributes redirectAttributes) {  // Redirect할 때 속성(Attribute)을 전달하는 스프링 인터페이스
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		// Redirect 경로까지 전달되는 속성 : Flash Attribute
		redirectAttributes.addFlashAttribute("name", name);   // addAttribute()가 아님을 주의하세요!(Model에 저장하는 거랑 똑같은 개념, forward할 때는 전달되지만 redirect할 때는 안 된다)
		redirectAttributes.addFlashAttribute("age", age);
		
		return "redirect:/post/list.me";
		
	}
	
	@GetMapping("/post/list.me")
	public String listMe() {  // Flash Attribute는 Redirect 경로까지 자동으로 전달되므로 별도의 코드가 필요하지 않다. 그냥 return 해라
		// ?붙여서 전달해준 것이 아니다. 파라미터로 받지마라.. 알아서 전달된다.
		return "post/list";
	}
	
}
