package com.gdu.app07.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.app07.domain.BoardDTO;
import com.gdu.app07.repository.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public List<BoardDTO> getBoardList() {
		return boardDAO.selectBoardList();
	}

	@Override
	public BoardDTO getBoardByNo(HttpServletRequest request) {
		// 파라미터 boardNo가 없으면(null, "") 0을 사용한다. (null은 택배가 오질 않았다. 빈 문자열은 열었는데 빈 상자)
		// 두가지를 다 대비하려면 고전타입으로 하면 된다. optional은 null만 처리가능, 빈 문자열 처리 못한다.
		// GET방식으로 하기때문에 주소창에 조작 가능해서.. null, 빈문자열 if로 처리
		String strBoardNo = request.getParameter("boardNo");
		int boardNo = 0;
		if(strBoardNo != null && strBoardNo.isEmpty() == false) {  // 이렇게 하면 된다. 그러나 원하지 않는 내용이 들어있을 경우를 잡지는 못함 -> 숫자가 아닌 것을 잡고싶으면 jsp에서 잡아라. 자바스크립트 메소드 ( isNan() )
			boardNo = Integer.parseInt(strBoardNo);
		} 
		return boardDAO.selectBoardByNo(boardNo);
	}
	
	@Override
	public int addBoard(HttpServletRequest request) {
		// 파라미터 title, content, writer 3개 받아온다고 생각. 안 받아오면 동작 안함!
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		// BoardDAO로 전달할 BoardDTO를 만든다.
		BoardDTO board = new BoardDTO();
		board.setTitle(title);
		board.setContent(content);
		board.setWriter(writer);
		return boardDAO.insertBoard(board);
	}
	
	@Override
	public int modifyBoard(HttpServletRequest request) {
		// 파라미터 title, content, boardNo를 받아온다.
		// 수정메소드 실행할 때 post방식으로 데이터 넘긴다. 그래서 주소창에 수작 못 부린다. null, 빈문자열 처리해줄 필요x
		// 실제로 boardNo가 무조건 온다고 보면 된다.  (boardNo가 없다? 개발자가 일하다가 만 것. 안 올 수가 없다)
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		// BoardDAO로 전달할 BoardDTO를 만든다.
		BoardDTO board = new BoardDTO();
		board.setTitle(title);
		board.setContent(content);
		board.setBoardNo(boardNo);
		return boardDAO.updateBoard(board);
	}

	@Override
	public int removeBoard(HttpServletRequest request) {
		// remove를 GET으로 요청하는 방법, POST로 요청하는 방법
		// GET은 구현이 쉽다. 만약에 누군가가 /remove.do?boardNo=1 이런 코드를 사용하는 것을 알았으면 권한도 없는 사용자가 주소에 입력하면 지워진다. 구현은 편하지만 삭제를 아는 사용자가 있으면 지워질 수 있다.
		// POST는 주소창에 조작 불가능. POST방식으로 바꿀 것이다. (코드로 조작할 수밖에 없다.<- 개발자 문제)
		// GET요청으로 되어있는 삭제쪽 jsp를 POST요청 방식으로 바꿔야 함.
		// 파라미터 boardNo를 받아온다. 
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		return boardDAO.deleteBoard(boardNo);
	}
}
