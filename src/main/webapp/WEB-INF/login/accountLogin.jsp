<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspHeader.jsp" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/accountLogin.css">
  
</head>
<body>
<jsp:include page="/WEB-INF/pageHeader.jsp"/>
	<h1>ログイン</h1>
	<jsp:include page="/WEB-INF/error_message_list.jsp"/>
	<form class="login-form" action="${pageContext.request.contextPath}/public/LoginServlet" method="post">
	
	<div class="form-input">
		<label>ID</label> 
		<input type="text" name="id" />
	</div>
	<div class="form-input">
		<label>パスワード</label>
		<input type="password" name="password" />
	</div>
	<div class="form-row">
	<button type="submit">ログイン</button>
	</div>
	<div class="form-row">
		<a href="${pageContext.request.contextPath}/public/AccountInputInitServlet">新規登録はこちら</a>
	</div>	
	</form>
</body>
</html>
<!--  http://localhost:8080/SunSelcoSpace/accountLogin.jsp -->