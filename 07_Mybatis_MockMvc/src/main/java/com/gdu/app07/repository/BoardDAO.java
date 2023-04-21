package com.gdu.app07.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app07.domain.BoardDTO;

// DBConfig에서 만들어준 Bean들 4개 + 트랜잭션매니저 Bean 컨테이너에 들어있다.
// 스프링컨테이너에 BoardDAO넣기
@Repository
public class BoardDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	// SqlSessionTemplate 가 매퍼 내용물 불러오는 거 가능
	
	// mapper namespace
	private final String NS = "mybatis.mapper.board.";
	
	// 1. 목록
	public List<BoardDTO> selectBoardList() {
		return sqlSessionTemplate.selectList(NS + "selectBoardList");
	}
	
	// 2. 상세
	// 서비스로부터 받아오는 int 타입의 boardNo를 받아올 수 있게 준비.. 
	// 서비스로부터 받아온 boardNo가 selectOne메소드의 매개변수로 사용되고 → 쿼리문의 변수로 넘어간다.(#{boardNo})
	// Service → DAO → 쿼리문 으로 넘어가는 것은 규칙이다! 함께 일하려면 필수로 지켜라
	// 쿼리문 → DAO → Service로 다시 반환
	public BoardDTO selectBoardByNo(int boardNo) {
		return sqlSessionTemplate.selectOne(NS + "selectBoardByNo", boardNo);
	}
	
	// 3. 삽입
	// insert 쿼리문이 전해주는 것은 0 아니면 1 => 그래서 반환타입 int다
	// 서비스에게 받아오는 것은? Board객체(title, content, writer도 들어있음)
	// ※ 서비스 구현할 때 title, content, writer 넣어주지 않으면 nullpointerException 발생한다. 서비스할 때 확인해야할 사항
	public int insertBoard(BoardDTO board) {
		return sqlSessionTemplate.insert(NS + "insertBoard", board);
	}
	
	// 4. 수정
	public int updateBoard(BoardDTO board) {
		return sqlSessionTemplate.update(NS + "updateBoard", board);
	}
	
	// 5. 삭제
	public int deleteBoard(int boardNo) {
		return sqlSessionTemplate.delete(NS + "deleteBoard", boardNo);
	}
	
	
}
