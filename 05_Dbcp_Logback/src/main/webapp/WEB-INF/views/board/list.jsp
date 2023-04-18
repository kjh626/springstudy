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
	tbody tr:hover{
		background-color: skyblue;
		cursor: pointer;
	}
</style>
<script>
	function fnDetail(n){
		location.href = '${contextPath}/board/detail.do?board_no=' + n;
	}
</script>
</head>
<body>

	<div>
		<a href="${contextPath}/board/write.do">새글작성</a>
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
						<td colspan="3">아직 게시글이 없습니다.</td>
					</tr>
				</c:if>
				<c:if test="${not empty boardList}">
					<c:forEach items="${boardList}" var="b">
						<tr onclick="fnDetail(${b.board_no})">  <%-- b.getTitle()처럼 자바문법으로 쓸 수도 있는데.. b.title이 그 일을 하는 거니 그냥 필드 호출하듯이 b.title로 써라 --%>
							<td>${b.title}</td>
							<td>${b.writer}</td>
							<td>${b.created_at}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

</body>
</html>