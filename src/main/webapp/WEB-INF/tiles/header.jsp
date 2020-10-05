<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <link href="<c:url value="/resources/css/reset.css" />"  rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
	<link href="<c:url value="/resources/css/main.css" />"  rel="stylesheet">
<header id="header">
		<div class="headerWrap">
			
			<div class="headerLeft">
				<img src="/resources/img/logo.png"  id="logo" class="headerLeftItems">
				<a href="#" class="headerLeftItems">Teach Q&A</a>
				<a href="#" class="headerLeftItems">스터디 구인</a>
				<a href="#" class="headerLeftItems">알고리즘 스터디</a>
			</div>
			<sec:authorize access="isAnonymous()">
			<div class="headerRight">
				<div class="search headerRightItems"><input type="text" placeholder="내용을 입력하세요"><i class="fas fa-search"></i></div>
				<a href="/devst/user/loginPage" class="headerRightItems">로그인</a>
				<a href="#" class="headerRightItems">회원가입</a>	
				<img src="https://placehold.it/64x64" class="headerLeftItems">
			</div>
			</sec:authorize>	
			<sec:authorize access="isAuthenticated()">		
			<div class="headerRight">
				<div class="search headerRightItems"><input type="text" placeholder="내용을 입력하세요"><i class="fas fa-search"></i></div>
				<a href="#" class="headerRightItems" id="user_info">회원정보</a>			
				<a href="#" class="headerRightItems" onclick="document.getElementById('logout-form').submit();">로그아웃</a>
				<img src="https://placehold.it/64x64" class="headerLeftItems">
				<form id="logout-form" action='<c:url value='/logout'/>' method="POST">
				   <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
				</form>
			</div>
			</sec:authorize>
		</div>
	</header>
	