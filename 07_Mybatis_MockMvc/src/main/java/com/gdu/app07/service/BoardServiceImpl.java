package com.gdu.app07.service;

import java.util.List;

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
	public BoardDTO getBoardByNo(int boardNo) {
		return boardDAO.selectBoardByNo(boardNo);
	}

	@Override
	public int addBoard(BoardDTO board) {
		return boardDAO.insertBoard(board);
	}

	@Override
	public int modifyBoard(BoardDTO board) {
		return boardDAO.updateBoard(board);
	}

	@Override
	public int removeBoard(int boardNo) {
		return boardDAO.deleteBoard(boardNo);
	}

}
