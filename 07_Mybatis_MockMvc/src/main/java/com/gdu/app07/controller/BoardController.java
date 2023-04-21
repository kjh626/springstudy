package com.gdu.app07.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app07.service.BoardServiceImpl;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	// BoardService라고 해도 되고, BoardServiceImpl이라고 타입을 선언해줘도 된다.
	// 인터페이스로 만들어져 있을 때는 인터페이스 타입으로 사용할 수 있기 때문에 예전에 인터페이스 타입으로 선언해준 것.
	private BoardServiceImpl boardService;
	
	// 전날 본 평가처럼 jsp로 받는 파라미터를 Controller에서는 (3가지 방법 중) request로 전부 받을 것이다. => request에서 알아서 꺼내서 쓰면 된다. 모든 파라미터 작업을 서비스가 할 것이다.
	
	/*
		데이터(속성) 저장 방법
		1. forward  : Model에 attribute로 저장한다.
		2. redirect : RedirectAttributes에 flashAttribute로 저장한다.
	*/
	
	@GetMapping("/list.do")
	// getBoardList() 서비스가 반환한 List<BoardDTO>를 WEB-INF/views/board/list.jsp로 전달한다. => 전달하려면 forward! forward할 데이터는 Model을 써야함
	public String list(Model model) {
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/list";
	}
	
	// 호랑이 시절 ModelAndView 클래스 (쓰는 코드도 한번) - 이거 쓰는 블로그 보면 호랑이 보고 뒷걸음치면 됨
	/*
	@GetMapping("/list.do")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardList", boardService.getBoardList());
		mav.setViewName("board/list");
		return mav;
	}
	*/
	
	// getBoardByNo() 서비스가 반환한 BoardDTO를 WEB-INF/views/board/detail.jsp로 전달한다.
	// jsp에서 전달하는 파라미터가 있다.파라미터(boardNo)를 받을 request를 매개변수로 선언할 필요가 있다.
	@GetMapping("/detail.do")
	public String detail(HttpServletRequest request, Model model) {
		model.addAttribute("b", boardService.getBoardByNo(request));
		return "board/detail";
	}
	
	// 매핑으로 요청이 오면 write.jsp로 곧바로 이동
	@GetMapping("/write.do")
	public String write() {
		return "board/write";
	}
	
	// addBoard() 서비스가 반환한 0 또는 1을 가지고 /board/list.do으로 이동(redirect)한다.  (addboard() → list.do → list.jsp)
	// 여기서 list.do로 보낸 0 또는 1의 값을 다시 forward하는 list.jsp까지 보낼 수 있다. => RedirectAttributes을 쓰면 된다. 메소드는 ★addFlashAttribute★ = Flash Flash =
	// 차이점 : 애트리뷰트로 실으면 list.do까지는 가지만, list.jsp까지는 못 간다. 플래시로 실으면 list.jsp까지 간다.
	//     => addBoard() 서비스가 반환한 0 또는 1은 WEB-INF/views/board/list.jsp에서 확인한다.  ( jsp에서 EL형태로 확인 가능 ${addResult} )
	@PostMapping("/add.do")
	public String add(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("addResult", boardService.addBoard(request));
		return "redirect:/board/list.do";
	}
	
	// modifyBoard() 서비스가 반환한 0 또는 1을 가지고 /board/detail.do으로 이동(redirect)한다.
	// modifyBoard() 서비스가 반환한 0 또는 1은 WEB-INF/views/board/detail.jsp에서 확인한다.
	@PostMapping("/modify.do")
	public String modify(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("modifyResult" ,boardService.modifyBoard(request));
		return "redirect:/board/detail.do?boardNo=" + request.getParameter("boardNo");
	}
	
	// removeBoard() 서비스가 반환한 0 또는 1을 가지고 /board/remove.do으로 이동(redirect)한다.
	// removeBoard() 서비스가 반환한 0 또는 1은 WEB-INF/views/board/remove.jsp에서 확인한다.
	@PostMapping("/remove.do")
	public String remove(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("removeResult" ,boardService.removeBoard(request));
		return "redirect:/board/list.do";
	}
	
}
