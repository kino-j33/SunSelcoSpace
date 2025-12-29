<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspHeader.jsp" %>

<div class="header">
	<!-- ロゴ（トップページリンク -->
 	<a href="${pageContext.request.contextPath}" class="logo">
 		<img src="<c:url value='/static/img/logo.png'/>" alt="サンセルコスペース ロゴ"></a>
 
 	<!-- ログインリンク（ログインしていない時）-->
 	<c:if test="${SESSION_KEY_LOGIN_ACCOUNT == null}">
 		<a href="${pageContext.request.contextPath}/public/LoginInitServlet">ログイン</a>
	</c:if>
 	
 	<!-- ログインしている時の表示 -->
 	<c:if test="${SESSION_KEY_LOGIN_ACCOUNT != null}">
 		<!-- アカウント名表示 -->
 		<span class="account-name">【<c:out value="${SESSION_KEY_LOGIN_ACCOUNT.name}"/>】</span>
 		
 		<!-- 予約状況確認リンク -->
 		<a href="${pageContext.request.contextPath}/BookingStatusInitServlet">予約状況確認</a>
 		
 		<!-- ログアウトリンク -->
 		<a href="${pageContext.request.contextPath}/LogoutServlet">ログアウト</a>
 	</c:if>
</div>
    