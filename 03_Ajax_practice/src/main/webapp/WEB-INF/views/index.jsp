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
<body>
	<h1>나는인덱스</h1>
	<div><a href="${contextPath}/first.prac">이동진1</a></div>
	<div><a href="${contextPath}/second.prac">이동2</a></div>
	<div><a href="${contextPath}/third.prac">이동3</a></div>
	<div><a href="${contextPath}/fourth.prac">이동4</a></div>
	<div><a href="${contextPath}/fifth.prac">이동5</a></div>
</body>
</html>