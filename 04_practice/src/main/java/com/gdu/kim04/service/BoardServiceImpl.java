package com.gdu.kim04.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.kim04.domain.BoardDTO;
import com.gdu.kim04.repository.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public List<BoardDTO> getBoardList() {
		List<BoardDTO> list = boardDAO.selectBoardList();
		return list;
	}

	@Override
	public BoardDTO getBoardByNo(int board_no) {
		BoardDTO board = boardDAO.selectBoardByNo(board_no);
		return board;
	}

	@Override
	public int addBoard(BoardDTO board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyBoard(BoardDTO board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeBoard(int board_no) {
		// TODO Auto-generated method stub
		return 0;
	}

}
