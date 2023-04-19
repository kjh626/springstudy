package com.gdu.app04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app04.domain.BoardDTO;
import com.gdu.app04.service.BoardService;

@RequestMapping("/board")  // 모든 mapping에 /board가 prefix로 추가됩니다.
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping("/list.do")
	public String list(Model model) {   // Model : jsp로 전달(forward)할 데이터(속성, attribute)를 저장한다. 기본적으로 Model의 구조는 Map으로 되어있다.(model을 써서 속성을 저장하면 실제로는 request에 저장되는 것이다.)
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/list";
	}
	
	@GetMapping("/write.do")
	public String write() {
		return "board/write";
	}
	
	@PostMapping("/add.do")
	public String add(BoardDTO board){  
		// 파라미터 받는방법 3가지 통일하자면 ①HttpServletRequest로 하는 거고, 상황봐서 알아서.. ②@RequestParam, ③BoardDTO board
		boardService.addBoard(board);      // addBoard 메소드의 호출 결과인 int 값(0 또는 1)은 사용하지 않았다.
		// 삽입이 끝났으니 리다이렉트 
		return "redirect:/board/list.do";  // 목록 보기로 redirect(redirect 경로는 항상 mapping으로 작성한다. jsp 적지마라)
	}
	/*  ①HttpServletRequest	
	 *  BoardDTO board = new BoardDTO();
		board.setTitle(request.getParameter("title"));
		board.setWriter(request.getParameter("content"));
		board.setContent(request.getParameter("content"));
		boardService.addBoard(board);
		return "board/list";
	}
	 */
	
	@GetMapping("/detail.do")
	// 코드의 편의상 @RequestParam <= optional 처리 , 번호가 아니면 0번 이런식으로 하려고   --> Optional로 처리하던 그 코드를 requestParam으로 쉽게
	// 목록보기, 상세보기는 Model이 필요하다. 모델이 있어야 상세보기jsp로 상세보기 내용을 전해줄 수 있다.
	public String detail(@RequestParam(value="board_no", required = false, defaultValue = "0") int board_no
			             , Model model) {
		model.addAttribute("b", boardService.getBoardByNo(board_no));
		return "board/detail";
	}
	
	@GetMapping("/remove.do")
	public String remove(@RequestParam(value="board_no", required = false, defaultValue = "0") int board_no) {
		boardService.removeBoard(board_no);
		return "redirect:/board/list.do";
	}
	
	@PostMapping("/modify.do")
	public String modify(BoardDTO board) {
		boardService.modifyBoard(board);
		// detail.do로 넘어가려면 파라미터로 board_no가 있어야 한다. 위에 매핑detail.do 받는 거 봐라.
		return "redirect:/board/detail.do?board_no=" + board.getBoard_no();
	}
	

	
}