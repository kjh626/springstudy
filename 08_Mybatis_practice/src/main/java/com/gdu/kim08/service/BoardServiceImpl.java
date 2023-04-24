package com.gdu.kim08.service;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.kim08.domain.BoardDTO;
import com.gdu.kim08.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDTO> getBoardList() {
		return boardMapper.selectBoardList();
	}

	@Override
	public BoardDTO getBoardByNo(HttpServletRequest request) {
		
		String strBoardNo = request.getParameter("boardNo");
		int boardNo = 0;
		if(strBoardNo != null && strBoardNo.isEmpty() == false) {
			boardNo = Integer.parseInt(strBoardNo);
		}
		
		return boardMapper.selectBoardByNo(boardNo);
	}

	@Override
	public void addBoard(HttpServletRequest request, HttpServletResponse response) {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		
		BoardDTO board = new BoardDTO();
		board.setTitle(title);
		board.setContent(content);
		board.setWriter(writer);
		
		int addResult = boardMapper.insertBoard(board);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(addResult == 1) {
				out.println("alert('게시글이 등록되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "/board/list.do'");
			} else {
				out.println("alert('게시글 등록 실패')");
				out.println("history.back()");
			}
			out.println("</script>");
			out.flush();
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void modifyBoard(HttpServletRequest request, HttpServletResponse response) {

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		BoardDTO board = new BoardDTO();
		board.setTitle(title);
		board.setContent(content);
		board.setBoardNo(boardNo);
		
		int modifyResult = boardMapper.updateBoard(board);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(modifyResult == 1) {
				out.println("alert('게시글이 수정되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "/board/detail.do'");
			} else {
				out.println("alert('게시글 수정 실패')");
				out.println("history.back()");
			}
			out.println("</script>");
			out.flush();
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void removeBoard(HttpServletRequest request, HttpServletResponse response) {

		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		int removeResult = boardMapper.deleteBoard(boardNo);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(removeResult == 1) {
				out.println("alert('게시글이 삭제되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "/board/list.do'");
			} else {
				out.println("alert('게시글 삭제 실패')");
				out.println("history.back()");
			}
			out.println("</script>");
			out.flush();
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void removeBoardListBoard(HttpServletRequest request, HttpServletResponse response) {

		String[] boardNoList = request.getParameterValues("boardNoList");
		
		int removeResult = boardMapper.deleteBoardList(Arrays.asList(boardNoList));
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(removeResult == boardNoList.length) {
				out.println("alert('선택된 모든 게시글이 삭제되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "/board/list.do'");
			} else {
				out.println("alert('선택된 게시글이 삭제되지 않았습니다.')");
				out.println("history.back()");
			}
			out.println("</script>");
			out.flush();
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getBoardCount() {
		int boardCount = boardMapper.SelectBoardCount();
		String msg = "[" + LocalDateTime.now().toString() + "] 게시글 갯수는 " + boardCount + "개입니다.";
		System.out.println(msg);
		

	}

}
