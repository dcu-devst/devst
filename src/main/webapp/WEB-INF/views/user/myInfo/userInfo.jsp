<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.myInfoWrap{width:1200px; margin:0 auto;}
.myInfoTitle{text-align: center;}
</style>
</head>
<body>
	<div class="myInfoWrap">
		<h1 class="myInfoTitle">내 정보</h1>
		<div class="myNickname">
			${myInfo.memNickname }
		</div>
		현재 : <img src="/resources/uploadImg/profile/15/${myInfo.memProfileImage }">
		<form action="/devst/user/info?${_csrf.parameterName}=${_csrf.token}"  method="post" enctype="multipart/form-data">
			<input type="file" name="multiFile" class="boardImg" accept="image/*">
			<input type="submit" value="변경">
			<input type="hidden" value="${myInfo.memId }" name="memId">
			<sec:csrfInput/>
		</form>
	</div>
</body>
</html>