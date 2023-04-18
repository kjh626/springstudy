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
</head>
<body>
	
	<!-- /detail.do 경로로 boardNo=1, title="제목" 파라미터로 보내기 -->
	<!-- 컨트롤러에서 detail.do 매핑 받는 메소드 2개 만들어줘야 함. 
		 왜? 하나는 @PostMapping으로 받아야 하고, 나머지는 @GetMapping 경로가 같아도 요청 방식이 다르면 다르게 처리할 수 있다. -->
	
	<!-- 1. <a> -->
	<div>
		<a href="${contextPath}/detail.do?boardNo=1&title=제목">보내기</a>
		<a href="${contextPath}/detail.do">보내기(파라미터없음)</a>
	</div>
	
	<!-- 2. <form>으로 보내기 . a링크와 다르게 post로 보낼 것임. 값을 숨겨서-->
	<div>  
		<form method="post" action="${contextPath}/detail.do">
			<input type="hidden" name="boardNo" value="1">
			<input type="hidden" name="title" value="제목">
			<button>보내기</button>
		</form>
		<!-- form에서 파라미터가 없다는 것은 input이 없다는 것, 정확하게는 name속성이 없다. --> 
		<form method="post" action="${contextPath}/detail.do">
			<button>보내기(파라미터없음)</button>
		</form>
	</div>
	
	<!-- 3. loaction -->
	<!-- form 밖에 있는 버튼은 그냥 일반 버튼임(submit이 없음) -->
	<div>
		<button onclick="fnMove()">보내기</button>
		<script>
			function fnMove() {
				location.href = '${contextPath}/detail.do?boardNo=1&title=제목'
			}
		</script>
	</div>
	
</body>
</html>