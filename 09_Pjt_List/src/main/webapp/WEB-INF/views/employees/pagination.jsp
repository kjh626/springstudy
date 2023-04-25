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
<script>
	$(function(){
		// recordPerPage의 변경
		$('#recordPerPage').on('change', function(){
			// alert($(this).val());
			location.href = '${contextPath}/employees/change/record.do?recordPerPage=' + $(this).val();  // session에 recordPerPage 올리기
		})
		
		// 세션에 저장된 recordPerPage값으로 <select> 태그의 값을 세팅
		let recordPerPage = '${sessionScope.recordPerPage}' == '' ? '10' : '${sessionScope.recordPerPage}';
		$('#recordPerPage').val(recordPerPage);
	})
</script>
</head>
<body>


	<%-- 화면이동이니까 search.form으로 해줬다 --%>
	<div>
		<a href="${contextPath}/employees/search.form">사원 조회 화면으로 이동</a>
	</div>
	
	<%-- 셀렉트 써서 한번에 몇개를 보여줄 것인지 --%>
	<div>
		<h1>사원 목록</h1>
		<div>
			<select id="recordPerPage">
				<option value="10">10개</option>
				<option value="20">20개</option>
				<option value="30">30개</option>
			</select>
		</div>
		<%-- select값이 change 되었다는 이벤트메소드가 필요하다. (recordPerPage)
		     그리고 이 정보를 어디에다가 저장해둘 것인가? 
		     파라미터는x => session으로 할 예정. 세션으로 저장하면 어디서든 빼서 쓸 수 있다.(페이지가 이동하더라도.. (10개) 여전히 유지하고 있을 것) 
		--%>
		
		<%-- 프로젝트 할 때는 <hr>쓰지 말고 css로 이쁘게 해라 --%>
		<hr>
		<table border="1">
			<thead>
				<tr>
					<td>순번</td>
					<td>사원번호</td>
					<td>사원명</td>
					<td>이메일</td>
					<td>전화번호</td>
					<td>입사일자</td>
					<td>직업</td>
					<td>연봉</td>
					<td>커미션</td>
					<td>매니저</td>
					<td>부서번호</td>
					<td>부서명</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${employees}" var="emp" varStatus="vs">
					<tr>
						<td>${vs.index}</td>
						<%-- empDTO에 있는 필드이름을 가져다 쓰는 거니까 잘 매칭해라 --%>
						<td>${emp.employeeId}</td>
						<td>${emp.firstName} ${emp.lastName}</td>
						<td>${emp.email}</td>
						<td>${emp.phoneNumber}</td>
						<td>${emp.hireDate}</td>
						<td>${emp.jobId}</td>
						<td>${emp.salary}</td>
						<td>${emp.commissionPct}</td>
						<td>${emp.managerId}</td>
						<td>${emp.deptDTO.departmentId}</td>
						<td>${emp.deptDTO.departmentName}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="12">
						<%-- 숫자 1 2 3 이렇게 만들어서 한번에 뿌리려고 이렇게 함 --%>
						<div>${pagination}</div>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
	
	
	
	
</body>
</html>