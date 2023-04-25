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


	<div>
		${productCount}개 제품이 있습니다.
		<h3>제품 등록 화면</h3>
		<%-- required: 필수 입력을 해야 전송(submit)이 된다. --%>
		<form method="post" action="${contextPath}/product/add.do">
			<div>
				<label for="prodName">제품이름</label>
				<input id="prodName" name="prodName" required="required">
			</div>
			<div>
				<label for="prodPrice">제품가격</label>
				<input id="prodPrice" name="prodPrice">
			</div>
			<div>
				<button>제품등록하기</button>
				<input type="reset" value="다시작성하기">
			</div>
		</form>
	</div>
	
	<hr>
	
	<div>
		<c:forEach items="${productList}" var="product">
			<ul>
				<li>제품번호 ${product.prodNo}</li>
				<li>제품이름 ${product.prodName}</li>
			</ul>
			<hr>
		</c:forEach>
	</div>







</body>
</html>