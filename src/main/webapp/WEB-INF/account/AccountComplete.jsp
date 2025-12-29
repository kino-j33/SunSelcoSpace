<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jp.co.sunselcospace.form.AccountForm" %>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ include file="/WEB-INF/jspHeader.jsp" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8"/>

<title>アカウント登録完了</title>

	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css">



<style>

h1 { font-size: 20px; margin-bottom: 16px; }
.row { margin: 8px 0; }
.label { display: inline-block; width: 160px; color: #555; }
.val { font-weight: normal; }
.note { margin-top: 16px; }
a { color: #0b63c5; text-decoration: none; }
</style>
</head>

<body style="text-align:center;">

<%
AccountForm form = (AccountForm) request.getAttribute("AccountForm");
String name = form != null ? form.getName() : "";
String id = form != null ? form.getId() : "";
%>

<jsp:include page="/WEB-INF/pageHeader.jsp"/>
<h1>アカウント登録完了</h1>
<div class="row">下記の通り アカウント情報を登録しました。</div>

<div class="row">
<span class="label">お名前</span>
<span class="val"><%= name %></span>
</div>
<div class="row">
<span class="label">ID</span>
<span class="val"><%= id %></span>
</div>
<div class="row">
<span class="label">パスワード</span>
<span class="val">表示されません。</span>
</div>

<div class="note">
<!-- ログイン画面に移動する場合は -->
<a href="<%= request.getContextPath() %>/public/LoginInitServlet">「ログイン」される場合はこちらをクリックしてください。</a>
</div>
</body>
</html>







