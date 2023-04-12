<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="${contextPath}/resources/css/init.css">
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script>
	$(function(){
		$('#title').text('Hello World');
	})
</script>
<body>

	<!-- MyController에서 확인합시다. -->	
	<h1 id="title"></h1>
	<div>
		<%-- src는 mapping(컨텍스트패스 아래 resources)과 연결, location은 실제 resources폴더를 말함. --%>
		<img src="${contextPath}/resources/images/animal1.jpg" width="300px">
	</div>
	<div>
		<a href="${contextPath}/list.do">목록보기</a>
	</div>
	
	<!-- ------------------------------------------------------------------------------------------------------ -->
	
	<!-- MvcController에서 확인합시다. -->
	<h1>요청 파라미터-1</h1>
	<div><a href="${contextPath}/detail.do">상세보기1</a></div>
	<div><a href="${contextPath}/detail.do?name=김정현">상세보기2</a></div>
	<div><a href="${contextPath}/detail.do?age=66">상세보기3</a></div>
	<div><a href="${contextPath}/detail.do?name=김정현&age=66">상세보기4</a></div>

	<h1>요청 파라미터-2</h1>
	<div><a href="${contextPath}/detail.me">상세보기1</a></div>
	<div><a href="${contextPath}/detail.me?name=김정현">상세보기2</a></div>
	<div><a href="${contextPath}/detail.me?age=66">상세보기3</a></div>
	<div><a href="${contextPath}/detail.me?name=김정현&age=66">상세보기4</a></div>
	
	<h1>요청 파라미터-3</h1>
	<div><a href="${contextPath}/detail.gdu">상세보기1</a></div>
	<div><a href="${contextPath}/detail.gdu?name=김정현">상세보기2</a></div>
	<div><a href="${contextPath}/detail.gdu?age=66">상세보기3</a></div>
	<div><a href="${contextPath}/detail.gdu?name=김정현&age=66">상세보기4</a></div>
		
	<!-- ------------------------------------------------------------------------------------------------------ -->
	
	<!-- DiController에서 확인합시다. -->
	<h1>Dependency Injection</h1>
	<!-- 매핑할 때 bbs맡은 사람의 detail (무슨 테이블의 detail, list. 앞에 테이블로 적어주면 편할듯?) 스프링에서는 자유롭게 매핑하면 된다. -->
	<!-- 선생님이 만든 웹페이지에서 볼 때 주소에 contextpath가 안 보이는데.. 배포할 때 배운다...  컨텍스트패스 변수 처리해놓으면 컨텍스트패스 없어도 인식한다. 변수처리! 꼭 해라 -->
	<div><a href="${contextPath}/bbs/detail.do">상세보기</a></div>
	

</body>
</html>