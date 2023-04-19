<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:set var="pageTitle" value="게시물 리스트"/>
						<%@ include file="../common/head.jspf" %>

			<table border="1">
			<thead>
			<tr>
			<th>번호</th>
			<th>작성날짜</th>
			<th>수정날짜</th>
			<th>작성자</th>
			<th>제목</th>		
				</tr>
		<tbody>
		<c:forEach var="article" items="${articles }">
		<fmt:formatDate value="${article.regDate }" pattern="yyyy-mm월-dd일" var="regDate"/>
		<fmt:formatDate value="${article.updateDate }" pattern="yyyy-mm월-dd일" var="updateDate"/>
		<tr>
		<td>${article.id }</td>
		<td>${regDate }</td>
		<td>${updateDate }</td>
		<td>${article.memberId }</td>
		<td><a class="titlename" href="../article/detail?id=${article.id }">${article.id }</a>${article.title }</td>
		</tr>
		</c:forEach>
		</tbody>
							
							</table>
						<%@ include file="../common/foot.jspf" %>