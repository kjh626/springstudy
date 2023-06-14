<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="${blog.blogNo }번 블로그" />
</jsp:include>
	
<style>
	.blind {
		display: none;
	}
</style>

	
<div>

	<h1>${blog.title }</h1>
	
	<div>
		<div>작성자 : ${blog.memberDTO.name }</div>
		<div>작성일 : ${blog.createdAt }</div>
		<div>수정일 : ${blog.modifiedAt }</div>
		<div>조회수 : ${blog.hit }</div>
	</div>
	
	<hr>
	
	<div>${blog.content }</div>

	<!-- onclick 이 아니고 아이디를 부여한 상태 -->
	<!-- 굳이 form을 쓴 이유: post방식으로 이동하기 위해서(주소창을 이용한 접근을 막기 위해, 누가 주소창으로 삭제할 수 없게함) -->
	<!-- GET방식의 대표적인 방법 : <a href=> , location.href 2가지 -->
	<div>
		<form id="frmBtn" method="post">
			<input type="hidden" name="blogNo" value="${blog.blogNo }">
			<c:if test="${sessionScope.loginId eq blog.memberDTO.id }">
				<input type="button" value="편집" id="btnEdit">
				<input type="button" value="삭제" id="btnRemove">
			</c:if>
			<input type="button" value="목록" id="btnList">
		</form>
		<script>
			function fnEdit(){
				$('#btnEdit').on('click', function(){
					$('#frmBtn').attr('action', '${contextPath }/blog/edit.do');
					$('#frmBtn').submit();
				})
			}
			function fnRemove(){
				$('#btnRemove').on('click', function(){
					if(confirm('블로그를 삭제하면 모든 댓글이 함께 삭제됩니다. 삭제할까요?')){
						$('#frmBtn').attr('action', '${contextPath }/blog/remove.do');
						$('#frmBtn').submit();
					}
				})
			}
			// list.do는 GET 매핑이다.
			function fnList(){
				$('#btnList').on('click', function(){
					location.href = '${contextPath }/blog/list.do';
				})
			}
			// 함수 호출
			fnEdit();
			fnRemove();
			fnList();
		</script>
	</div>

	<hr>
	
  <!-- 댓글 구역 -->
  
  <div id="btnGood" style="width: 100px; border: 1px solid silver;">
    <span id="heart"></span>
    <span id="good">좋아요</span>
    <span id="goodCount"></span>
  </div>
  <script>
  
  </script>
  
  <div>
    <form id="frmAddComment">
      <input type="text" name="content" id="content" placeholder="댓글 작성해 주세요">
      <input type="hidden" name="blogNo" value="${blog.blogNo}">
      <input type="hidden" name="memberNo" value="${sessionScope.loginNo}">
      <input type="button" value="작성완료" id="btnAddComment">
    </form>
    <script>
	 // 내가 예전에 "좋아요"를 누른 게시글인지 체크하는 함수
    	function fnGoodCheckState(){
	      $.ajax({
	        type: 'get',
	        url: '${contextPath}/good/getGoodCheckState.do',
	        data: 'blogNo=${blog.blogNo}',
	        dataType: 'json',
	        success: function(resData){         // resData = {"userGoodCount": 0}
	          if(resData.userGoodCount == 0){   // "좋아요"를 안 누른 게시글이면 하얀하트(heart1.png) 표시, 아니면 빨간하트(heart2.png) 표시
	            $('#heart').html('<img src="${contextPath}/resources/images/heart1.png" width="15px">');
	            $('#good').removeClass("goodChecked");
	          } else {
	            $('#heart').html('<img src="${contextPath}/resources/images/heart2.png" width="15px">');
	            $('#good').addClass("goodChecked");
	          }
	        }
	      })
	    }
	    // 게시글의 "좋아요" 개수를 표시하는 함수
	    function fnGoodCount(){
	      $.ajax({
	        type: 'get',
	        url: '${contextPath}/good/getGoodCount.do',
	        data: 'blogNo=${blog.blogNo}',
	        dataType: 'json',
	        success: function(resData){  // resData = {"blogGoodCount": 10}
	          $('#blogGoodCount').empty();
	          $('#blogGoodCount').html(resData.blogGoodCount + '개');
	        }
	      })
	    }
	    // "좋아요"를 눌렀을 때 동작하는 함수
	    function fnGoodPress(){
	      $('#btnGood').on('click', function(){
	        // 로그인을 해야 "좋아요"를 누를 수 있다.
	        if('${sessionScope.loginId}' == ''){
	          if(confirm('해당 기능은 로그인이 필요한 기능입니다. 로그인할까요?')){
	            location.href = '${contextPath}/index.do';
	          }
	        }
	        // 셀프 "좋아요" 방지
	        if('${sessionScope.loginId}' == '${blog.memberDTO.id}'){
	          alert('본인의 게시글에는 "좋아요"를 누를 수 없습니다.');
	          return;
	        }
	        // "좋아요" 선택/해제 상태에 따른 아이콘 변경
	        $('#good').toggleClass("goodChecked");
	        if ($('#good').hasClass("goodChecked")) {
	          $('#heart').html('<img src="${contextPath}/resources/images/heart2.png" width="15px">');
	        } else {
	          $('#heart').html('<img src="${contextPath}/resources/images/heart1.png" width="15px">');              
	        }
	        // "좋아요" DB 처리
	        $.ajax({
	          type: 'get',
	          url: '${contextPath}/good/mark.do',
	          data: 'blogNo=${blog.blogNo}',
	          dataType: 'json',
	          success: function(resData){  // resData = {"isSuccess", true}
	            if(resData.isSuccess) {
	              fnGoodCount();             
	            }
	          }
	        });
	      })
	    }
      function fnLoginCheck(){
    	  $('#content').on('focus', function(){
    		  if('${sessionScope.loginId}' == ''){
    			  if(confirm('로그인이 필요한 기능입니다. 로그인할까요?')){
    				  location.href = '${contextPath}/index.do';
    			  }
    		  }
    	  })
      }
      function fnAddComment(){
    	  $('#btnAddComment').on('click', function(){
    		  if($('#content').val() == ''){
    			  alert('댓글 내용을 입력하세요.');
    			  return;
    		  }
    		  $.ajax({
    			  type: 'post',
    			  url: '${contextPath}/comment/addComment.do',
    			  data: $('#frmAddComment').serialize(),
    			  dataType: 'json',
    			  success: function(resData){  // resData = {"isAdd": true}
    				  if(resData.isAdd){
    					  alert('댓글이 등록되었습니다.');
    					  $('#content').val('');
    					  fnCommentList();  // 댓글 목록을 가져와서 화면에 만드는 함수
    				  }
    			  }
    		  })
    	  })
      }
      // 페이징할 떄 현재 몇 페이지인가를 알아야 한다.
      // 전역 변수. 이렇게 해주면 page 는 무조건 전달된다고 보면 됨.
      var page = 1;
      
   // 내가 예전에 "좋아요"를 누른 게시글인지 체크하는 함수
      function fnGoodCheckState(){
        $.ajax({
          type: 'get',
          url: '${contextPath}/good/getGoodCheckState.do',
          data: 'blogNo=${blog.blogNo}',
          dataType: 'json',
          success: function(resData){         // resData = {"userGoodCount": 0}
            if(resData.userGoodCount == 0){   // "좋아요"를 안 누른 게시글이면 하얀하트(heart1.png) 표시, 아니면 빨간하트(heart2.png) 표시
              $('#heart').html('<img src="${contextPath}/resources/images/heart1.png" width="15px">');
              $('#good').removeClass("goodChecked");
            } else {
              $('#heart').html('<img src="${contextPath}/resources/images/heart2.png" width="15px">');
              $('#good').addClass("goodChecked");
            }
          }
        })
      }
      // 게시글의 "좋아요" 개수를 표시하는 함수
      function fnGoodCount(){
        $.ajax({
          type: 'get',
          url: '${contextPath}/good/getGoodCount.do',
          data: 'blogNo=${blog.blogNo}',
          dataType: 'json',
          success: function(resData){  // resData = {"blogGoodCount": 10}
            $('#blogGoodCount').empty();
            $('#blogGoodCount').html(resData.blogGoodCount + '개');
          }
        })
      }
      // "좋아요"를 눌렀을 때 동작하는 함수
      function fnGoodPress(){
        $('#btnGood').on('click', function(){
          // 로그인을 해야 "좋아요"를 누를 수 있다.
          if('${sessionScope.loginId}' == ''){
            if(confirm('해당 기능은 로그인이 필요한 기능입니다. 로그인할까요?')){
              location.href = '${contextPath}/index.do';
            }
          }
          // 셀프 "좋아요" 방지
          if('${sessionScope.loginId}' == '${blog.memberDTO.id}'){
            alert('본인의 게시글에는 "좋아요"를 누를 수 없습니다.');
            return;
          }
          // "좋아요" 선택/해제 상태에 따른 아이콘 변경
          $('#good').toggleClass("goodChecked");
          if ($('#good').hasClass("goodChecked")) {
            $('#heart').html('<img src="${contextPath}/resources/images/heart2.png" width="15px">');
          } else {
            $('#heart').html('<img src="${contextPath}/resources/images/heart1.png" width="15px">');              
          }
          // "좋아요" DB 처리
          $.ajax({
            type: 'get',
            url: '${contextPath}/good/mark.do',
            data: 'blogNo=${blog.blogNo}',
            dataType: 'json',
            success: function(resData){  // resData = {"isSuccess", true}
              if(resData.isSuccess) {
                fnGoodCount();             
              }
            }
          });
        })
      }
      
      function fnChangePage(){
          $(document).on('click', '.enable_link', function(){
            page = $(this).data('page');
            fnCommentList();
          })
        }
      
      // 이벤트를 이렇게 쓰는 것은 동적으로 만든 객체 => 자바스크립트로 만든 객체들은 이벤트를 이렇게 만들어준다.
      // 토글 jquery 4장 DOM_operate 에 있음
      function fnToggleReplyArea(){
    	  $(document).on('click', '.btnOpenReply', function(){
    		  $(this).next().toggleClass('blind');
    	  })
      }
      
      function fnAddReply(){
          $(document).on('click', '.btnAddReply', function(){
            if($(this).prevAll('.replyContent').val() == ''){  // $(this).prevAll('.replyContent') : 클릭한 .btnAddReply 버튼의 이전 요소 중에서 class="replyContent"인 요소 (답글을 작성하는 input 요소에 class="replyContent"가 추가되어 있음)
              alert('답글 내용을 입력하세요.');
              return;
            }
            $.ajax({
              type: 'post',
              url: '${contextPath}/comment/addReply.do',
              data: $(this).parent('.frmReply').serialize(),  // 클릭한 .btnAddReply 버튼의 부모 <form class="frmReply">을 의미한다.
              dataType: 'json',
              success: function(resData){  // resData = {"isAdd": true}
                if(resData.isAdd){
                  alert('답글이 등록되었습니다.');
                  fnCommentList();
                }
              }
            })
          })
        }
      
      // 좋아요
      fnGoodCheckState();
      fnGoodCount();
      fnGoodPress();
      
      // 댓글
      fnLoginCheck();
      fnAddComment();
      fnCommentList();
      fnChangePage();
      
      // 답글
      fnToggleReplyArea();
      fnAddReply();
    </script>
	</div>
	
	<div>
		<div id="commentList"></div>
		<div id="pagination"></div>
	</div>
	<script>
		
	</script>


</div>

</body>
</html>