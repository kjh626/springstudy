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
  
    <h1>회원 가입</h1>
  
    <div>* 표시는 필수 입력사항입니다.</div>
    
    <hr>
    
    <form id="frmJoin" method="post" action="${contextPath}/user/join.do">
    	
    	<!-- agree.jsp에서 전달된 location, event 속성 -->
    	<input type="hidden" name="location" value="${location}">
    	<input type="hidden" name="event" value="${event}">
    	
    	<!-- id의 ①정규식체크, ②아이디의 중복체크(DB까지 갔다와야 알 수 있음)
    	     ajax를 써서 아이디체크를 해야함(화면 안 바꾸고 DB갔다오려면..)
    	     span에 체크 결과 내용이 나올 수 있도록 해준다.(틀렸다, 중복이다, 둘다 통과->사용할 수 있는 id다 등) -->
      <div>
        <label for="id">아이디*</label>
        <input type="text" name="id" id="id">
        <span id="msgId"></span>
      </div>
      
      <div>
        <label for="pw">비밀번호*</label>
        <input type="password" name="pw" id="pw">
        <span id="msgPw"></span>
      </div>
      
      <div>
        <label for="rePw">비밀번호 확인*</label>
        <input type="password" id="rePw">
        <span id="msgRePw"></span>
      </div>
      
      <div>
        <label for="name">이름*</label>
        <input type="text" name="name" id="name">
      </div>
      
      <div>
        <span>성별*</span>
        <input type="radio" name="gender" id="none" value="NO" checked="checked">
        <label for="none">선택 안함</label>
        <input type="radio" name="gender" id="male" value="M">
        <label for="male">남자</label>
        <input type="radio" name="gender" id="female" value="F">
        <label for="female">여자</label>
      </div>
    
      <div>
        <label for="mobile">휴대전화*</label>
        <input type="text" name="mobile" id="mobile">
        <span id="msgMobile"></span>
      </div>
    
      <div>
        <label for="birthyear">생년월일*</label>
        <select name="birthyear" id="birthyear"></select>
        <select name="birthmonth" id="birthmonth"></select>
        <select name="birthdate" id="birthdate"></select>       
      </div>
      
      <div>
      </div>
      
      <div>
        <label for="email">이메일*</label>
        <input type="text" name="email" id="email">
        <input type="button" value="인증번호받기" id="btnGetCode">
        <span id="msgEmail"></span><br>
        <input type="text" id="authCode" placeholder="인증코드 입력">
        <input type="button" value="인증하기" id="btnVerifyCode">
      </div>
      
      <hr>
      
      <div>
        <button>가입하기</button>
        <input type="button" value="취소하기">
      </div>
    
    </form>
  
  </div>
  
</body>
</html>