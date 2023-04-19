<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<style>
.titlename{
color:black;
text-decoration:none;
}

</style>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="/resource/common.css"/>
<script src="/resource/common.js" defer="defer"></script>
	</head>
	<body>
	<h1>게시물 리스트 페이지<h1>
	<header>
	<a href="/">로고</a>
	<ul>
	<li><a href="/">홈</a></li>
	<li><a href="/usr/article/list">리스트</a></li>
	</ul>
	</header>
	<hr/>
	<table border="1">
	<thead>
	<tr>
	<th>번호</th>
	<th>작성날짜</th>
	<th>수정날짜</th>
	<th>작성자</th>
	<th>제목</th>		
		</tr>				<tbody>
		<c:forEach var="article" items="${articles }">
		<fmt:formatDate value="${article.regDate }" pattern="yyyy-mm월-dd일" var="regDate"/>
		<fmt:formatDate value="${article.updateDate }" pattern="yyyy-mm월-dd일" var="updateDate"/>
		<tr>
		<td>${article.id }</td>
		<td>${regDate }</td>
		<td>${updateDate }</td>
		<td>${article.memberId }</td>
		<td><a class="titlename" href="../article/detail?id=${article.title }">${article.title }</a></td>
						
							
		</tr>
		</c:forEach>
		</tbody>
		
		</table>
</body>
</html>