<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.css">
<style>
	tbody tr:hover {
		background-color: beige;
		cursor: pointer;
	}
</style>
<script>
	function fnDetail(n) {
		location.href = '${contextPath}/board/detail.do?boardNo=' + n;
	}
	$(function(){
		// 삽입을 했을 땐 addResult에 값을 저장시켜주지만, index에서 넘어갈 때는 addResult값을 보내지 않는다.
		// ${addResult} 가 항상 있는 게 아니다. 전달된 값이 없을 수도 있다. addResult가 전달이 안 되면 -> 비어있다. let addResult = ;  이 상태다 => 이대로 실행하면 빨간줄
		//let addResult = ${addResult};  // EL은 항상 변수 처리되는 부분이다. 이렇게 변수 만들면 안 된다. -> '' 로 문자열로 만들어준다.
		let addResult = '${addResult}';   // let addResult = '1';  삽입 성공
		                                  // let addResult = '0';  삽입 실패
		                                  // let addResult = '';   삽입과 상관 없음
		if(addResult != '') {
			if(addResult == '1'){
				alert('게시글이 등록되었습니다.');
			} else{
				alert('게시글 등록이 실패했습니다.');
			}
		}

		let removeResult = '${removeResult}';
		if(removeResult != ''){
			if(removeResult == '1') {
				alert('게시글이 삭제되었습니다.');
			} else {
				alert('게시글 삭제가 실패했습니다.');
			}
		}
		
	})
</script>
</head>
<body>

	<div>
		<a href="${contextPath}/board/write.do">새글작성하기</a>
	</div>
	
	<div>
		<table border="1">
			<thead>
				<tr>
					<td>제목</td>
					<td>작성자</td>
					<td>작성일자</td>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty boardList}">
					<tr>
						<td colspan="3">첫 게시글의 주인공이 되어 보세요!</td>
					</tr>
				</c:if>
				<c:if test="${not empty boardList}">					
					<c:forEach items="${boardList}" var="b">
						<tr onclick="fnDetail(${b.boardNo})">
							<td>${b.title}</td>
							<td>${b.writer}</td>
							<td>${b.createdAt}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	
</body>
</html>