package com.gdu.app09.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
// 값들을 가져다 쓸 수 있게 Getter 써줌
@Getter
public class PageUtil {

	private int page;               // 현재 페이지(필요한 정보 파라미터로 받아온다.)
	private int totalRecord;        // 전체 레코드 개수(DB에서 구해온다)
	private int recordPerPage;      // 한 페이지에 표시할 레코드 개수(파라미터로 받아온다)
	private int begin;              // 한 페이지에 표시할 레코드의 시작 번호(계산한다)
	private int end;                // 한 페이지에 표시할 레코드의 종료 번호(계산한다)
	
	private int pagePerBlock = 5;   // 한 블록에 표시할 페이지의 개수(임의로 정한다)
	private int totalPage;          // 전체 페이지 개수(계산한다)           
	private int beginPage;          // 한 블록에 표시할 페이지의 시작 번호(계산한다)
	private int endPage;            // 한 블록에 표시할 페이지의 종료 번호(계산한다)
	
	// 받아올 거 3개, 안 받아오면 계산 못 한다.
	public void setPageUtil(int page, int totalRecord, int recordPerPage) {

		// page, totalRecord, recordPerPage 저장
		this.page = page;
		this.totalRecord = totalRecord;
		this.recordPerPage = recordPerPage;
		
		// begin, end 계산
		/*
 			totalRecord==26, recordPerPage==5인 상황
			page    begin    end
			1       1        5
			2       6        10
			3       11       15
			4       16       20
			5       21       25
			6       26       26
		*/
		begin = (page - 1) * recordPerPage + 1;
		end = begin + recordPerPage - 1;
		if(end > totalRecord) {   // 30이 26보다 크다. 그래서 조건식으로 오게되고 end는 26이 된다.
			end = totalRecord;
		}
		
		// totalPage 계산
		totalPage = totalRecord / recordPerPage;
		if(totalRecord % recordPerPage != 0) {
			totalPage++;
		}
		
		// beginPage, endPage 계산
		/*
			totalPage=6, pagePerBlock=4인 상황
			block(page)	beginPage	endPage
			1(1~4)		1			4
			2(5~6)		5			6
    	*/
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = beginPage + pagePerBlock - 1;
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
	}
	
	
	public String getPagination(String path) {
		
		StringBuilder sb = new StringBuilder();
		
		//   1 2 3 4 5 > 이런 모양새
		// < 6 7 8 9 10 >
		// < 11
		// 3가지 관점: 이전 블록 / 페이지번호 / 다음 블록
		
		sb.append("<div class=\"pagination\">");
		
		// 이전 블록 : 1블록은 이전 블록이 없고, 나머지 블록은 이전 블록이 없다.
		if(beginPage == 1) { // 1블록
			sb.append("<span class=\"hidden\">◀</span>");
		} else {	// 나머지 블록
			sb.append("<a class=\"link\" href=\""+ path + "?page=" + (beginPage - 1) +"\">◀</a>");
		}
		
		// 페이지번호 : 현재 페이지는 링크가 없다.
		for(int p = beginPage; p <= endPage; p++) {
			if(p == page) {
				sb.append("<span class=\"strong\">" + p + "</span>");
			} else {
				sb.append("<a class=\"link\" href=\""+ path +"?page=" + p + "\">" + p + "</a>");
			}
		}
		
		// 다음 블록 : 마지막 블록은 다음 블록이 없고, 나머지 블록은 다음 블록이 있다.
		if(endPage == totalPage) {
			sb.append("<span class=\"hidden\">▶</span>");
		} else {
			sb.append("<a class=\"link\" href=\"" + path +"?page=" + (endPage + 1) + "\">▶</a>");
		}
		sb.append("</div>");
		
		return sb.toString();
		
	}
	
}
