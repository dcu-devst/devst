<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<%-- <meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="_csrf" content="${_csrf.token}" /> --%>
<meta charset="UTF-8">
<title>devst</title>
<link href="<c:url value="/resources/css/reset.css" />"  rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<link href="<c:url value="/resources/css/main.css" />"  rel="stylesheet">
<!-- <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script> -->
<script src="/resources/js/main.js"/></script>


<sec:authentication property="principal" var="loginUser"/>


</head>
<body>
<!-- <div id="certified">

</div> -->
<%-- dd : ${loginUser } --%>

 <%-- dd : ${loginUserSec }<br>
 dd : ${loginUser }<br>
id : ${loginUser.memEmail } --%>




   <%-- <header id="header">
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
            <a href="#" class="headerRightItems"><sec:authentication property="principal.memNickname"/>님</a>         
            <a href="#" class="headerRightItems" onclick="document.getElementById('logout-form').submit();">로그아웃</a>
            <img src="https://placehold.it/64x64" class="headerLeftItems">
            <form id="logout-form" action='<c:url value='/logout'/>' method="POST">
               <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
            </form>
         </div>
         </sec:authorize>
      </div>
   </header> --%>
   <nav id="nav">
      <ul class="navWrap">
         <li class="navItems">날짜순</li>
         <li class="navItems">인기순</li>
         <li class="navItems">조회순</li>
         <li class="navItems">댓글순</li>
      </ul>
   </nav>
   
   
   <main id="main">
      <div class="mainWrap">
      
      <!--<c:forEach var="list" items = "${boardList }" >
      
      <tr>
         <td>${list.brdId }</td>
         <td>${list.brdTitle }</td>
         <td>${list.brdHashtag }</td>
         <td>${list.brdCategory }</td>
      </tr>
      
      </c:forEach>  -->
         <c:forEach  var="list" items = "${boardList }" >
         <div class="mainItems" onclick="boardDetailOne(${list.brd_id}, '${list.brd_category }')">
            <c:if test="${list.brd_category eq '일반'}">
               <div class="categoryColorBlue"></div>
            </c:if>
            <c:if test="${list.brd_category eq '스터디구인'}">
               <div class="categoryColorGreen"></div>
            </c:if>
            <h3 class="mainItemsTitle">${list.brd_title }</h3>
            <div class="mainItemsMid">
               <div class="mainItemsMidLeft">
                  <div class="mainItemsMidLeftTop">
                     <div class="mainItemsGood"><i class="far fa-thumbs-up"></i><p class="goodCnt">${list.brd_like_count }</p></div>
                     
                  </div>
                  <div class="mainItemsMidLeftBot">
                     <div class="mainItemsComment"><i class="far fa-comment"></i><p class="comment">10</p></div>
                     <div class="mainItemsBad"><i class="fas fa-eye"></i><p class="viewCnt">${list.brd_view_count }</p></div>
                  </div>
               </div>
               <div class="mainItmesMidRight">
                  <div class="mainItmesNm"><i class="fas fa-user-circle"></i><p>${list.mem_nickname }</p></div>
                  <div class="mainItmesDate"><p>${list.brd_update_date }</p></div>
               </div>
            </div>
            <div class="mainItemsDescWrap">
            <p class="mainItemsDesc">
               ${list.brd_content }
            </p>
         </div>
         </div>
         </c:forEach>
         
         <!--mainItems  원형 -->
            <!-- <div class="mainItems">
            <div class="categoryColor"></div>
            <h3 class="mainItemsTitle">리엑트 스터디 구함</h3>
            <div class="mainItemsMid">
               <div class="mainItemsMidLeft">
                  
                  <div class="mainItemsViews"><img src="https://placehold.it/35x35"><p class="viewCnt">0</p></div>
                  <div class="mainItemsMidLeftTop">
                     <div class="mainItemsBad"><img src="https://placehold.it/35x35"><p class="badCnt">15</p></div>
                     <div class="mainItemsGood"><img src="https://placehold.it/35x35"><p class="goodCnt">45</p></div>
                  </div>
               </div>
               <div class="mainItmesMidRight">
                  <div class="mainItmesNm"><img src="https://placehold.it/35x35"><p>닉네임</p></div>
                  <div class="mainItmesDate"><p>20/09/12</p></div>
               </div>
            </div>
            <div class="mainItemsDescWrap">
            <p class="mainItemsDesc">
               이번에 제가 서울에서 리엑트 모입을 하려고...
            </p>
         </div>
         </div> -->
            
      </div>
   </main>

   
   

   
</body>
</html>