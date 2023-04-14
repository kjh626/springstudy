<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script>

	function fn1(){
		$.ajax({
			type: 'post',            // 서버로 보낼 데이터를 요청 본문(request body)에 저장해서 보낸다.    => get방식으로 처리할 수 없다. 왜냐? 파라미터가 없어서! 불가능하다.! 세트처럼 같이 생각해라 post, data:JSON.stringify
			url: '${contextPath}/third/ajax1',
			// 우리가 보낼 데이터를 json데이터로 만들기. 이렇게 만들어서 우리는 서버측으로 보낼 예정. (컨트롤러로)
			// 자바스크립트 객체를 문자열 형식을 제이슨타입으로 바꿔주는 역할을 한다. 별도의 라이브러리가 필요하지 않다. (webstudy - js - 11장) :객체 → JSON(문자열) 변환하기. JSON형태의 문자열
			// 자바스크립트 내장객체
			data: JSON.stringify({   // 문자열 형식의 JSON 데이터를 서버로 보낸다. 파라미터 이름이 없음에 주의한다.(서버에서 파라미터로 받을 수 없다.) -> 지금은 값만 보내고 있다.  => 서비스에 request 안 쓴 이유: 파라미터를 보내지 않기 때문에.. 파라미터로 받을 수 없다. 객체하고 맵만 준비해놓음
									 // (같은이름으로 만들어준) name이라는 필드와 tel이라는 필드를 가진 해당 객체만 받을 수 있다. 마찬가지로 맵도 받을 수 있다. Jackson이 같은 이름으로 된 필드를 가진 객체, 맵 찾아서 데이터 변환시켜서 넣어줌
				// 한줄씩 적어준다.
				'name': $('#name').val(),  // name이라는 프로퍼티로 객체를 만들고
				'tel': $('#tel').val()     // tel이라는 프로퍼티로 객체를 만든다.
			}),
			// 예시 - data: '{"name": "kim", "tel": "010-1111-5555"}'  <- 문자열밖에 없다.(파라미터 이름이 없음.)
			contentType: 'application/json',   // 서버로 보내는 data의 타입을 서버에 알려준다.  (그냥 json이라고 쓰면 오류난다. 컨트롤러는 그런거 몰라..)
			// 응답
			dataType: 'json',
			success: function(resData){        // resData = {"name": "민경태", "tel": "010-1234-1234"} 이렇게 받아온다고 생각해라
				// 알아서 닫는다 (</ul> 안 적어줘도)
				let str = '<ul>';
				str += '<li>' + resData.name;
				str += '<li>' + resData.tel;
				$('#result').html(str);    // append쓰지 말고 html써라 (append는 원래 있던 거 지우기(empty()) 후에 추가하기 했었음.)
										   // html은 기존에 꺼 덮어쓰기로 된다.
			},
			error: function(jqXHR){
				if(jqXHR.status == 400){
					alert('이름과 전화번호는 필수입니다.');
				}
			}
		})
	}
	
	function fn2(){
		
	}
	
</script>
</head>
<body>

	<div>
		<form id="frm">
			<div>
				<label for="name">이름</label>
				<input id="name" name="name">
			</div>
			<div>
				<label for="tel">전화번호</label>
				<input id="tel" name="tel">
			</div>
			<div>
				<input type="button" value="전송1" onclick="fn1()">
				<input type="button" value="전송2" onclick="fn2()">
			</div>
		</form>
	</div>
	
	<hr>
	
	<div id="result"></div>
	
		
</body>
</html>