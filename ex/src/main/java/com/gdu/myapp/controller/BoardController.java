package com.gdu.myapp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.myapp.domain.BoardDTO;

@Controller
public class BoardController {
	
	// ※ 문자열 배열을 써서 매핑 여러개 넣어줄 수 있다.
	@GetMapping({"/", "/index.do"})  // "/"        : http://localhost:9090/myapp 요청인 경우에 동작한다. (context path 경로)
                                     // "index.do" : http://localhost:9090/myapp/index.do 요청인 경우에 동작한다. 
	/*
	@GetMapping("/board")
	@GetMapping("board")
	    => 두개 매핑은 같은 거다. 구글링할 때 슬래시 있는 거 없는 거 있다.
	*/
	// web component들의 context path 는 webapp이다. 프로젝트 설계상 만들어진 것이다. 직접 폴더를 만들더라도 src/main/webapp 이렇게 만들어 줘야 한다.
	// 반환타입 String : 이동할 jsp 이름을 반환한다. 반환된 이름은 servlet-context.xml의 ViewResolver에 의해서 rendering된다. (prefix + 반환값 + suffix)
	public String index() {
		return "index";
	}
	
	// 반환타입이 void이면(반환타입이 없을 때는) 매핑의 이름이 jsp인 줄 안다. detail을 jsp이름으로 인식한다.(.do 도 알아서 빼주고..)
	// <a>, location
	/*
	@GetMapping("/detail.do")
	public void getDetail(HttpServletRequest request) {
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String title = request.getParameter("title");
		System.out.println(boardNo + ", " + title);
	}
	*/
	
	// <form>
	/*
	@PostMapping("/detail.do")
	public void postDetail(HttpServletRequest request) {
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String title = request.getParameter("title");
		System.out.println(boardNo + ", " + title);
	}
	*/
	
	
	// 파라미터가 많지 않을 때 좋은 방법
	// DB까지 간다고 치면 다시 BoardDTO로 합쳐야 한다. 
	// 장점 : 파라미터가 안 온다면 default처리 하겠음. => 이런 용도로 쓸 때 좋다. 
	/*
	@GetMapping("/detail.do")
	public void getDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo
						, @RequestParam(value="title", required=false, defaultValue="빈제목") String title) {
		System.out.println(boardNo + ", " + title);
	}
	
	@PostMapping("/detail.do")
	public void postDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo
					     , @RequestParam(value="title", required=false, defaultValue="빈제목") String title) {
		System.out.println(boardNo + ", " + title);
	}
	*/
	
	
	/*
	@GetMapping("/detail.do")
	public void getDetail(BoardDTO board) {   // board.setBoardNo()와 board.setTitle()이 여기서 사용된다.
		System.out.println(board);   // BoardDTO 객체를 출력하면 BoardDTO 클래스의 toString()이 동작한다.
		필드값 없으면 0, null, false 뜬거 배웠지?
	}
	
	@PostMapping("/detail.do")
	public void postDetail(BoardDTO board) {   // board.setBoardNo()와 board.setTitle()이 여기서 사용된다.
		System.out.println(board);   // BoardDTO 객체를 출력하면 BoardDTO 클래스의 toString()이 동작한다.
	}
	*/
	
	
	
	// Model 
	// 1. 주 목적 : jsp로 forward할 데이터(attribute)를 저장하는 용도
	// 2. (jsp의) 속성(attribute) 저장소 4개(pageContext, request, session, application) 중에서 request를 이용
	// 3. 컨트롤러에서만 선언할 수 있다. (컨트롤러에서 매개변수로 쓰이는 것들 : request, response, session, model 컨트롤러에서 선언하고, 서비스로 주던가 이런식으로 사용)
	
	/*
	@GetMapping("/detail.do")
	public String getDetail(HttpServletRequest request, Model model) {
		// 파라미터가 없을 때 대비를 해줘야 해서 Optional써줌
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("boardNo"));
		int boardNo = Integer.parseInt(opt1.orElse("0"));
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("title"));
		String title = opt2.orElse("빈제목");
		model.addAttribute("boardNo", boardNo);   // request.setAttribute("boardNo", boardNo); 이렇게 써도 에러없이 잘 되지만 하지마라 죽는다.
		model.addAttribute("title", title);		  // request.setAttribute("title", title);
		return "detail";
	}
	
	@PostMapping("/detail.do")
	public String postDetail(HttpServletRequest request, Model model) {
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("boardNo"));
		int boardNo = Integer.parseInt(opt1.orElse("0"));
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("title"));
		String title = opt2.orElse("빈제목");
		model.addAttribute("boardNo", boardNo);
		model.addAttribute("title", title);
		return "detail";
	}
	 */
	
	/*
	@GetMapping("/detail.do")
	public String getDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo
						, @RequestParam(value="title", required=false, defaultValue="빈제목") String title
						, Model model) {
		model.addAttribute("boardNo", boardNo);
		model.addAttribute("title", title);
		return "detail";
	}
	
	@PostMapping("/detail.do")
	public String postDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo
					     , @RequestParam(value="title", required=false, defaultValue="빈제목") String title
					     , Model model) {
		model.addAttribute("boardNo", boardNo);
		model.addAttribute("title", title);
		return "detail";
	}
	*/
	
	
	@GetMapping("/detail.do")
	public String getDetail(BoardDTO board) {   // 파라미터를 객체로 받으면 자동으로 Model에 저장이 된다! Model을 선언할 필요도 없다. 속성명은 boardDTO로 저장이 된다!(클래스를 이용해서 속성명을 만든다!)
		return "detail";
	}
	
	@PostMapping("/detail.do")
	public String postDetail(@ModelAttribute("board") BoardDTO board) {  // Model에 저장하는 속성명을 "board"로 하겠다.
		return "detail";
	}
	
	
	
}
