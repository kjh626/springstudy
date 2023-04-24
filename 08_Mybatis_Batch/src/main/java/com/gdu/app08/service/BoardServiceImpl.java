package com.gdu.app08.service;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.app08.domain.BoardDTO;
import com.gdu.app08.mapper.BoardMapper;

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
		
		// 파라미터 title, content, writer 3개 받아온다고 생각. 안 받아오면 동작 안함!
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		// BoardDAO로 전달할 BoardDTO를 만든다.
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
				out.println("alert('게시글이 등록되지 않았습니다.')");
				out.println("history.back()");
			}
			out.println("</script>");
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void modifyBoard(HttpServletRequest request, HttpServletResponse response) {
		
		// 파라미터 title, content, writer 3개 받아온다고 생각. 안 받아오면 동작 안함!
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		// BoardDAO로 전달할 BoardDTO를 만든다.
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
				out.println("alert('게시글이 수정되지 않았습니다.')");
				out.println("history.back()");
			}
			out.println("</script>");
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void removeBoard(HttpServletRequest request, HttpServletResponse response) {

		// 파라미터 boardNo를 받아온다. 
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
				out.println("alert('게시글이 삭제되지 않았습니다.')");
				out.println("history.back()");
			}
			out.println("</script>");
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeBoardList(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터 boardNoList를 받아온다. 
		String[] boardNoList = request.getParameterValues("boardNoList");
		int removeResult = boardMapper.deleteBoardList(Arrays.asList(boardNoList));    // Arrays.asList(boardNoList)) : String[] boardNoList를 ArrayList로 바꾸는 코드. (매퍼쪽으로 어레이리스트 전달하기로 했으니까. 그약속 지켰다)
		// 3개를 전달해서 3개를 지웠다면 removeResult 가 3이 나온다.
		
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void getBoardCount() {
		
		int boardCount = boardMapper.SelectBoardCount(); 
		String msg = "[" + LocalDateTime.now().toString() + "] 게시글 개수는 " + boardCount + "개입니다.";
		System.out.println(msg);
		
	}
		
		
	
	
}
