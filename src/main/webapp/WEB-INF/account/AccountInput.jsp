<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.co.sunselcospace.constant.Constant" %>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ include file="/WEB-INF/jspHeader.jsp" %>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8"/>
<title>アカウント登録入力</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css">
<style>

h1 { font-size: 20px; margin-bottom: 16px; }
.form-row { margin: 10px 0; }
.label { display: inline-block; width: 120px; }
.errors { color: #b00020; margin: 12px 0; }
.errors li { margin-left: 16px; }
.actions { margin-top: 16px; }
.note { margin-top: 24px; font-size: 14px; }
input[type="text"], input[type="password"] { width: 240px; padding: 6px; }
button { padding: 6px 12px; }
a { color: #0b63c5; text-decoration: none; }
</style>
</head>
<body style="text-align:center;">

<jsp:include page="/WEB-INF/pageHeader.jsp"/>
<h1>アカウント登録入力</h1>

<%
List<String> errorMessageList = (List<String>) request.getAttribute(Constant.ERROR_MESSAGE_LIST);
if (errorMessageList != null && !errorMessageList.isEmpty()) {
%>
<div class="errors">
<ul>
<% for (String msg : errorMessageList) { %>
<li><%= msg %></li>
<% } %>
</ul>
</div>
<% } %>

<form method="post" action="<%= request.getContextPath() %>/public/AccountConfirmationServlet">
<div class="form-row">
<span class="label">お名前</span>
<input type="text" name="name" maxlength="15" required />
</div>
<div class="form-row">
<span class="label">ID</span>
<input type="text" name="id" maxlength="8" required />
</div>
<div class="form-row">
<span class="label">パスワード</span>
<input type="password" name="password" maxlength="20" required />
</div>
<div class="actions">
<button type="submit">入力情報を確認</button>
</div>
</form>

<div class="note">
すでに登録されている方は
<a href="<%= request.getContextPath() %>/public/LoginInitServlet">こちらからログイン</a>
</div>
</body>
</html>



