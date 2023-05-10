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
		<h1>${upload.uploadNo}번 UPLOAD 게시글</h1>
		<ul>
			<li>제목 : ${upload.uploadTitle}</li>
			<li>내용 : ${upload.uploadContent}</li>
			<li>작성일자 : ${upload.createdAt}</li>
			<li>수정일자 : ${upload.modifiedAt}</li>
		</ul>
	</div>
	
	<hr>
	
	<div>
		<h4>첨부 목록 및 다운로드</h4>
		<div>
			<c:forEach items="${attachList}" var="attach">
				<div>
					<a href="${contextPath}/upload/download.do?attachNo=${attach.attachNo}">
						<c:if test="${attach.hasThumbnail == 1}">
							<%-- 썸네일이 없으면 정적 이미지 파일 가져다 쓰면 되는데, 있을 경우가 어떻게 표시할 지가 문제다. 
								이전에 배움-> 3장에서 이미지 경로와 이름을 받아와서 바이트배열로 데이터를 받아와서 뿌려주는 형식으로 연습했었음. --%>
							<%-- 경로,이름 정보 넘겼었는데, 첨부번호만 넘기면 다 알 수 있으니 이거 하나만 넘기자.
								 /upload/display?attachNo 이 서비스가 어떻게 동작되는 지를 잘 이해해봐라 --%>
							<img src="${contextPath}/upload/display.do?attachNo=${attach.attachNo}">
						</c:if>
						<c:if test="${attach.hasThumbnail == 0}">
						<%-- servlet-context.xml에 resources관련 태그 있음. /resources/ 매핑은 우리가 이미지 저장해놓은 웹앱의 resources폴더로 연결해준다. --%>
							<img src="${contextPath}/resources/images/attach1.png" width="50px">
						</c:if>
						${attach.originName}
					</a>
				</div>
			</c:forEach>
			<div>
				<a href="${contextPath}/upload/downloadAll.do?uploadNo=${upload.uploadNo}">모두 다운로드</a>
			</div>
		</div>
	</div>
	
</body>
</html>