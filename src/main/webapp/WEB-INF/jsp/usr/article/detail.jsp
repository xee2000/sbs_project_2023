<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../common/head.jspf"%>
<section class="mt-5">
		<table border="1">
				<tr>
						<th>번호</th>
						<td>${article.id }</td>
				<tr>
						<th>작성날짜</th>
						<td>${article.regDate }</td>
				<tr>
						<th>수정날짜</th>
						<td>${article.updateDate }</td>
				<tr>
						<th>작성자</th>
						<td>${article.memberId }</td>
				<tr>
						<th>제목</th>
						<td>${article.title }</td>
				<tr>
						<th>내용</th>
						<td>${article.body }</td>
				</tr>

		</table>
		<div>
				<button type="button" onclick="history.back();">뒤로가기</button>
		</div>


</section>

<%@ include file="../common/foot.jspf"%>