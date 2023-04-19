package com.gdu.app06.service;

import java.util.List;

import com.gdu.app06.domain.BoardDTO;

public interface BoardService {
	public List<BoardDTO> getBoardList();
	public int addBoard(BoardDTO board);
	public BoardDTO getBoardByNo(int board_no);
	public int modifyBoard(BoardDTO board);
	public int removeBoard(int board_no);
}
