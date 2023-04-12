package com.gdu.app02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.app02.domain.Bbs;

@Controller
public class DiController {
	
	/*
		Spring Container에 저장되어 있는 Bean을 가져올 수 있는 Annotation
		    └ 컨테이너에 Bean 저장하는 방법은 2가지 (① xml: <bean> , ② java: @Configuration, @Bean)
		1. @Inject
			1) Bean의 타입(class)이 일치하는 Bean을 가져온다.
			2) 동일 타입의 Bean이 2개 이상이면 오류가 발생한다.
				 예)Person p1 = new Person("kim");
					Person p2 = new Person("lee");
   					=> 타입으로만 구분하기 때문에 같은 Bean으로 본다
			3) 동일 타입의 Bean을 구별하기 위해서 @Qualifier를 사용할 수 있다.
			
		2. @Resource
			1) Bean의 이름(id)이 일치하는 Bean을 가져온다.
			2) 동일한 이름의 Bean이 없으면 오류가 발생한다.
			
		★★3. @Autowired - 자동으로 묶어준다(Inject 기반) -> 쓰는 방법 3가지 ★★
			1) Bean의 타입(class)이 일치하는 Bean을 가져온다.
			2) 동일 타입의 Bean이 2개 이상이면 Bean의 이름(id)이 일치하는 Bean을 가져온다.(더블 체크: ①클래스 체크 → ②아이디 체크)
			3) 이걸 쓴다!!!!!!!!!!!!!!!
	*/
	
	/*
		@Autowired 사용 방법 3가지
		
		1. 필드에 @Autowired 선언하기
			1) 필드에 자동으로 Bean을 주입한다.
			2) 각 필드마다 @Autowired를 선언한다.
			3) 필드가 10개이면 @Autowired도 10번 선언해야 한다. (필드가 많은 경우 사용하지 않는다.)
			
		2. 생성자에 @Autowired 선언하기 (필드 2개 이상이면 생성자로 만들어주면 편하다)
			1) 생성자의 매개변수(괄호 안에 있는 변수)에 있는 객체들에 자동으로 Bean을 주입한다.
			2) 생성자에는 @Autowired 선언을 생략할 수 있다. (일반적으로 생략한다.)
			※ 컨트롤러에 생성자가 있으면 그것은 100% Autowired 때문이다.
			
		3. 메소드에 @Autowired 선언하기
			1) 메소드의 매개변수(괄호 안에 있는 변수)에 있는 객체들에 자동으로 Bean을 주입한다.
			2) 메소드에는 @Autowired 선언을 생략할 수 없다.
	*/
	/* 필드에 @Autowired 선언하기
                                 	//┌──── Spring Container ──────┐ 타입(class)은 중복이네? 그다음에 이름 체크하고 bbs1, bbs2  <= 더블 체크
	@Autowired private Bbs bbs1;	//│<bean id="bbs1" class="Bbs">│  		
	@Autowired private Bbs bbs2;	//│@Bean public Bbs bbs2() { } │
									  └────────────────────────────┘
	*/
	/*
	private Bbs bbs1;
	private Bbs bbs2;
	// 컨트롤러에서 필드로 저장하는 것은 이런 게 아니라 Service다. 같은 타입인 걸(동일한 Bean 2개) 걱정할 필요 없다.  ( doGet() → Service → Repository )
	// 동일한 Service를 2번 선언할 필요 없음. DAO의 경우도 똑같.. (실무에선 거의 일어날 일 없다)
	
	/* 생성자에 @Autowired 선언하기
	public DiController(Bbs bbs1, Bbs bbs2) {   // 매개변수로 스프링컨테이너에 저장된 빈이 자동으로 주입되면 이게 필드에 주입된다. 결과적으로 필드에 주입된다.
		super();
		this.bbs1 = bbs1;
		this.bbs2 = bbs2;
	}
	*/
	//메소드에 @Autowired 선언하기
	/*@Autowired
	public void method(Bbs bbs1, Bbs bbs2) {
		this.bbs1 = bbs1;
		this.bbs2 = bbs2;
	}
	*/
	@Autowired private Bbs bbs1;
	@Autowired private Bbs bbs2;
	
	@GetMapping("/bbs/detail.do")
	public String detail(Model model) {
		
		model.addAttribute("bbs1", bbs1);
		model.addAttribute("bbs2", bbs2);

		return "bbs/detail";    // 실제 처리 경로 : /WEB-INF/views/bbs/detail.jsp
	}
	
	
}
