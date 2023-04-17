package com.gdu.kim04.service;

import java.util.List;

import com.gdu.kim04.domain.BoardDTO;

public interface BoardService {
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardByNo(int board_no);
	public int addBoard(BoardDTO board);
	public int modifyBoard(BoardDTO board);
	public int removeBoard(int board_no);
	
}
