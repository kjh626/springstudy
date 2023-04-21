package com.gdu.app07.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gdu.app07.domain.BoardDTO;

public interface BoardService {
	// DAO 로부터 ArrayList를 받아온다. 이 ArrayList를 서비스를 호출하는 곳(Controller)으로 전달하려고 한다. 
	// 다오로부터 전달받은 값을 그대로 컨트롤러로 전달해주려고 한다.
	public List<BoardDTO> getBoardList();
	// jsp로부터 받는 정보를 Controller는 request로 전부 받게 해주려 함.
	public BoardDTO getBoardByNo(HttpServletRequest request);
	// 결과처리는 Controller로 넘겨두고 구현하려 한다.
	// 서비스에서는 로직을 짜는 것이다. request에 있는 정보를 가지고 BoardDTO를 만들어서 DAO로 전달해줄 필요가 있다.(DAO에서는 BoardDTO로 받으니까)
	public int addBoard(HttpServletRequest request);
	public int modifyBoard(HttpServletRequest request);
	// 삭제는 request에서 boardNo만 뽑아주면 됨.
	public int removeBoard(HttpServletRequest request);
}
