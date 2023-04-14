package com.gdu.app02.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app02.domain.Person;

@Controller
public class MvcController {
	
	/*
		@RequestMapping을 대체하는 새로운 애너테이션(Spring4이후 제공함. 버전업을 안 하면 new 레거시프로젝트는 (3.몇)
		1. @GetMapping  : GET 방식일 때 쓴다
		2. @PostMapping : POST 방식일 때 쓴다.
		3. @PutMapping  : PUT 방식(수정할 때 쓴다)
		4. @DeleteMapping
	*/
	
	/*
		요청 파라미터의 UTF-8 인코딩 처리(복사해서 써라)
		
		메소드마다 request.setCharacterEncoding("UTF-8")을 작성하는 것은 매우 비효율적이므로,
		모든 요청(contextPath를 가진 모든 요청)마다 동작하도록 filter를 사용한다.
		CharacterEncodingFilter를 통해서 모든 요청마다 자동으로 UTF-8로 인코딩된다.
		참고할 파일 : /WEB-INF/web.xml
	*/
	
	/* 1. HttpServletRequest로 요청 파라미터 처리하기 -> 요청마다 메소드에 적어줘야 함. jsp서블릿은 한번에 처리했는데.. */
	
	@GetMapping("/detail.do")
	public String detail(HttpServletRequest request, Model model) {
		
		// name의 전달이 없으면 "홍길동"으로 사용된다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("name"));
		String name = opt1.orElse("홍길동");

		// age의 전달이 없으면 "0"이 사용된다.
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("age"));
		int age = Integer.parseInt(opt2.orElse("0"));
		
		/*
			Model model
			1. 스프링에서 사용하는 데이터(속성) 전달 객체이다.
			2. Model2(Jsp/Servlet)에서는 HttpServletRequest request 객체를 사용해서 데이터를 전달하지만(요청도 받고 응답도 보내주고 jsp에서는 request가 다 함),
				스프링에서는 Model model 객체를 사용한다.(spring에서는 forward를 위해서 model(응답 처리)을 사용한다. (요청 받고(request), 응답 보내주기(model) => forward)
			3. forward할 데이터를 Model의 addAttribute() 메소드로 저장한다.
			※ request 사용하는 방법도 아직 많이 쓴다. 확장성이 좋음..(ip주소..)
		*/
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		// 기본 이동 방식은 forward
		return "mvc/detail";    // 실제 처리 경로 : /WEB-INF/views/mvc/detail.jsp 이리로 가는 건 알겠다. redirect하는 건가? forward 하는 건가?
		
		/*
			참고. redirect로 이동하기
			return "redirect:이동할 경로";
		*/
		
	}
	
	
	/*
		2. @RequestParam으로 요청 파라미터 처리하기 
			1) value        : 요청 파라미터 이름
			2) required     : 요청 파라미터의 필수 여부(디폴트는 true) -> 필수인데 파라미터 전달 없으면 오류남
			3) defaultValue : 요청 파라미터가 없을 때 대신 사용할 값
			장점: 파라미터(String)를 Integer.parseInt 할 필요 없어짐. 알아서 한다. 
			변수만 선언해줘도 된다. @RequestParam자체를 생략할 수도 있다. @RequestParam(value="name", required=false, defaultValue="홍길동") String name -> String name 
																		  @RequestParam(value="int", required=false, defaultValue="0") int age -> int age
	*/
	@GetMapping("/detail.me")
	public String detailMe(@RequestParam(value="name", required=false, defaultValue="홍길동") String name,  // 요청 파라미터 name이 없으면 "홍길동"을 사용한다.
						   @RequestParam(value="int", required=false, defaultValue="0") int age,           			    // 요청 파라미터 age가 없으면 0을 사용한다.
						   Model model) {
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "mvc/detail";
		
	}
	
	/*
	@GetMapping("/detail.me")              // @RequestParam을 생략할 수 있다.
	public String detailMe(String name,    // 요청 파라미터 name이 없으면 null이 저장된다.	
    					   int age,        // 요청 파라미터 age가 없으면 null을 int로 변환하려고 하기 때문에 오류가 발생한다. (굳이 이 방식으로 받아야겠다? String age로 하면 됨)
			               Model model) {
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "mvc/detail";
	}
	*/
	
	// name, age를 한번에 저장하고 싶으면 Bean으로 만들어서(class Person) 받을 수도 있다.
	
	/*
		3. 커맨드 객체를 이용한 요청 파라미터 처리
			1) 파라미터를 필드로 가진 객체를 커맨드 객체라고 한다.
			2) setter가 사용된다.
			3) 자동으로 Model에 저장된다.
	*/
	
	@GetMapping("/detail.gdu")
	public String detailGdu(Person p) {    // name과 age를 필드로 가진 커맨드 객체 Person p
										   // Model에 저장될 때 객체 이름인 p를 사용하지 않고,
										   // 객체 타입인 Person을 사용한다.
										   // Model에 저장되는 속성명은 객체 타입 Person을 person으로 수정해서 사용한다.(첫글자만 소문자로 바꿔서 사용한다 - 스프링이 알아서..)
		return "mvc/detail";
	}
	/*
	@GetMapping("/detail.gdu")
	public String detailGdu(@ModelAttribute(value="p") Person person) {   // Model에 저장할 속성명을 p로 해 주세요!
																		  // ${p.name}, ${p.age}와 같은 형식으로 확인할 수 있다.
		return "mvc/detail";
	}
	*/
	
}
