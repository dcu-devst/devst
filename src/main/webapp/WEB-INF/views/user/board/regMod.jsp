<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="guestMode" value=""></c:set>
<c:if test="${oneInfo ne null }">
	<c:set var="guestMode" value="readonly"></c:set>
</c:if>
<c:set var="boardList" value="/devst/board/category?no=2"></c:set>
<c:if test="${oneInfo.brd_category eq '일반'}">
<c:set var="boardList" value="/devst/board/category?no=1"></c:set>
</c:if>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal"  var="loginUserSec"/>						
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.boardWrap{width:1200px;  margin:0 auto;}
	.boardBox, .boardCommentBox{margin:0 auto; width:800px;}
	
	.boardCommentDesc{border:1px solid #ddd; box-sizing: border-box; margin:10px 0;}
	
	.boardBox{margin-bottom: 100px;}
	.boardCategory, .boardTitle, .boardHashtag, .boardWriter{width:800px; height:30px; margin: 10px 0;}
	.boardCategory{width:810px;}
	.boardContent{width:800px; height:600px;}
	.doBtn, .doModBtn{position:absolute; bottom:-61px;right:0; width: 90px;}
	.cancleBtn{position:absolute; bottom:-60px; left:0; width: 90px;}
	.btnBox{width:800px;position:relative;}
	
	.like , .comment{color: black; line-height: 25px;  font-size: 25px;}
	.clickBox{margin: 10px  0;}
	#msg{color:red;}
	.profileImg{ float:left; margin-right: 20px;}
	.profileImg > img{width: 120px; height: 120px;}
	.boardCommentWriter{text-align: center; margin-bottom: 0;}
	.boardCommentContent{padding-top: 20px;}
	.boardCommentDesc{overflow: hidden; position: relative;}
	.boardCommentBtn{position: absolute; right: 10px; bottom: 0;}
	.boardCommentBtn > i{font-size: 20px; cursor:pointer; margin:10px;}
	.boardCommentBtn > i:nth-child(1):hover{color:blue;}
	.boardCommentBtn > i:nth-child(2):hover{color:red;}
	.doWriteBox{overflow: hidden; position: relative; height: 180px;}
	.doWriteContent{width:658px; padding: 0; height: 120px; font-size: 16px; padding-top: 20px;}
	.doWriteBtn{position: absolute; right: 3px; bottom: 0px;}
</style>
</head>
<body>
	
	<a href="${boardList }">게시글 더 보기</a>
	${loginUserSec }
	
	<div class="boardWrap">
		<div class="boardBox">
			<form action="/devst/board/regmod?${_csrf.parameterName}=${_csrf.token}" id="frm" method="post" enctype="multipart/form-data">
			
			<%-- <input type="hidden" value="${oneInfo.mem_id }" name="memId"><!-- 이 값이 있고, db에도 해당값이있다면 수정으로 판명 --> --%>
			
			<select name="brdCategory" class="boardCategory" >
			    <option value="스터디구인">스터디구인</option>
			    <option value="일반">일반게시판</option>
			    <option value="abc">lorem</option>
			    <option value="123">lorem</option>
			</select>
				<c:if test="${guestMode ne '' }" >
					<input type="text" readonly class="boardWriter" value="작성자 : ${oneInfo.mem_nickname }">
				</c:if>
				<input type="text" placeholder="제목을 입력해주세요" name="brdTitle" class="boardTitle" ${guestMode }  value="${oneInfo.brd_title }">
				<input type="text" placeholder="hashtag" name="brdHashtag" class="boardHashtag">
				<textarea name="brdContent" class="boardContent" ${guestMode } >${oneInfo.brd_content }</textarea>
				<div class="btnBox">
				<c:if test="${guestMode eq '' }" >
					<input type="file" name="multiFile" class="boardImg" accept="image/*">
				</c:if>
					<c:if test="${guestMode eq ''}" >
						<button type="submit" class="btn btn-dark doBtn">&nbsp 작성 &nbsp</button>
					</c:if>
					<sec:authorize access="isAuthenticated()">
						<c:if test="${oneInfo.mem_nickname eq loginUserSec.memNickname}">
							<button type="button" class="btn btn-dark doModBtn" onclick="doMod(${oneInfo.brd_id})">수정</button>
						</c:if> 
					</sec:authorize>
					<button type="button" class="btn btn-success cancleBtn" onclick="doCancle()">취소</button>
				</div>
				 
			</form>
			
		</div>
		
		<div class="boardCommentBox">
			<c:if test="${guestMode ne ''}" >
				<a href="#">${oneInfo.mem_nickname }님의 게시글 더보기 > </a>			
			</c:if>
			<div class="clickBox">
				<span class="like">좋아요</span><i class="far fa-thumbs-up">24</i>
				<span class="comment">댓글</span><i class="far fa-comment">12</i>
			</div>
			<h3 class="commentTitle">댓글</h3>
			<div class="boardCommentWrap">
			<!-- 댓글 원형 -->
			<!-- <div class="boardComment">
				
				<div class="boardCommentDesc">
					<div class="profileImg">
						<img src="https://placehold.it/120x120">
						<p class="boardCommentWriter">작성자</p>
					</div>
					
					<p class="boardCommentContent">안녕하세요 안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요ㅍ </p>
					<div class="boardCommentBtn">
						<i class="far fa-thumbs-up">24</i>
						<i class="far fa-thumbs-down">24</i>
					</div>
				</div>
			</div> -->
			<!--  -->
			
		
			<c:forEach items="${commentList}" var="comment">
				<div class="boardComment">
					
					<div class="boardCommentDesc">
						<div class="profileImg">
						 <c:if test="${comment.profile_img eq ''}">
						 	<img src="/resources/img/defaultProfile.jpg">
						 </c:if>
						 <c:if test="${comment.profile_img ne ''}">
						 	<img src="${comment.profile_img }">
						 </c:if>
							
							<p class="boardCommentWriter">${comment.mem_nickname }</p>
						</div>
						
						<p class="boardCommentContent">${comment.content }</p>
						<div class="boardCommentBtn">
							<i class="far fa-thumbs-up">${comment.like_count }</i>
							<i class="far fa-thumbs-down">${comment.dislike_count }</i>
						</div>
					</div>
				</div>		
			
			</c:forEach>
			
			
			
			</div>
			<!--  -->
			<h3 >댓글작성</h3>
			
				<div class="doWriteBox">
					<div class="profileImg">
					<sec:authorize access="isAuthenticated()">
						<c:if test="${loginUserSec.memProfileImage eq null }">
							<img src="/resources/img/defaultProfile.jpg">	
						</c:if>
						<c:if test="${loginUserSec.memProfileImage ne null }">
							<img src="/resources/uploadImg/profile/${loginUserSec.memId }/${loginUserSec.memProfileImage}">	
						</c:if> 
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<img src="/resources/img/defaultProfile.jpg">
					</sec:authorize>
						
						<p class="boardCommentWriter">작성자</p>
					</div>
					<textarea maxlength="150" class="doWriteContent" placeholder="150자이내 작성"></textarea>
					<button type="button" class="btn btn-primary doWriteBtn">작성</button>
				</div>
				
			
			
		</div>
		
	</div>
	<p id="msg">${msg }</p>
	<script>
		function doCancle(){
			if(confirm('정말 취소하시겠습니까?')){
				location.href='/devst';
			}	
		}
		
		function doMod(id){//수정
			var current = $('.doModBtn').html();
			if(current == '수정완료'){
				$('#frm').submit();
			} else if(current == '수정'){
				alert('수정을 시작합니다.')
				$('.doModBtn').html('수정완료')		
				$('.doModBtn').removeClass('btn-dark');
				$('.doModBtn').addClass('btn-primary');
				$('.boardCategory').attr('disabled', false);
				$('.boardTitle').attr('readonly', false);
				$('.boardContent').attr('readonly', false);
				
				var currentQueryStr = window.location.href.split("?")[1];//현재 페이지 쿼리스트링을 주어서 수정성공시 다시 페이지로 돌아오게하기위함
				$('#frm').attr('action','/devst/board/detail/update?${_csrf.parameterName}=${_csrf.token}&'+currentQueryStr);
			}
			
		}
		
		
		
		$(function(){
			if('${guestMode}' == 'readonly'){//방문자일때 selectbox disabled
				var selectVal = '${oneInfo.brd_category}';
				$('.boardCategory').val(selectVal).prop('selected',true)
				$('.boardCategory').attr('disabled',true);
				$('.boardContent, .boardTitle, .boardWriter').css('backgroundColor','#fafafa')
				
			}
			
			
			//댓글작성
			$('.doWriteBtn').click(function(){
				if('${loginUserSec }' == ''){
					alert("로그인 후 이용해주세요")
					return;
				} 
				
				var header = $("meta[name='_csrf_header']").attr("content");
	        	var token = $("meta[name='_csrf']").attr("content");
				$.ajax({
					type:"post",
					url:"/devst/user/board/comment",
					data: {
						memId:'${loginUserSec.memId }',//로그인유저
						brdId:'${oneInfo.brd_id }',//게시글no
						content:$('.doWriteContent').val()
					},
					dataType:'json',
					beforeSend: function(xhr){
						xhr.setRequestHeader(header, token);	// 헤드의 csrf meta태그를 읽어 CSRF 토큰 함께 전송
					},
					success:function(result){
						console.log(result.jsonData)
						var item = '<div class="boardComment">'
										+'<div class="boardCommentDesc">'
											+'<div class="profileImg">'
												+'<img src="https://placehold.it/120x120">'
												+'<p class="boardCommentWriter">${loginUserSec.memNickname}</p>'
											+'</div>'
											+'<p class="boardCommentContent">'+result.jsonData.content+'</p>'
											+'<div class="boardCommentBtn">'
												+'<i class="far fa-thumbs-up">'+result.jsonData.likeCount+'</i>'
												+'<i class="far fa-thumbs-down">'+result.jsonData.dislikeCount+'</i>'
											+'</div>'
										+'</div>'
									'+</div>';
						
						$('.boardCommentWrap').prepend(item);
						$('.doWriteContent').val('');
						alert('댓글작성에 성공했습니다.')
						if('${loginUserSec.memProfileImage}' != null){
							$('.boardComment:nth-child(1)').find('.profileImg > img').attr('src','/resources/img/defaultProfile.jpg')
						} else {
							$('.boardComment:nth-child(1)').find('.profileImg > img').attr('src','${loginUserSec.memProfileImage}')
						}
					},error:function(){
						console.log('댓글 ajax 에러')
					}
				})		
			})
					
			
		})
		
		
	
	</script>
</body>
</html>
