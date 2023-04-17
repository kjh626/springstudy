package com.gdu.kim04.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.kim04.domain.BoardDTO;

@Repository
public class BoardDAO {
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	
	private Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GDJ61", "1111");
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void close() {
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(con != null) con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<BoardDTO> selectBoardList() {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		list.add(new BoardDTO(1, "제목", "내용", "작성자", "작성일", "수정일"));
		return list;
	}
	
	public BoardDTO selectBoardByNo(int board_no) {
		BoardDTO board = new BoardDTO(board_no, "제목", "내용", "작성자", "작성일", "수정일");
		return board;
	}
	
	public int insertBoard(BoardDTO board) {
		
		int insertResult = 1;
		
		return insertResult;
	}
	
	
}
