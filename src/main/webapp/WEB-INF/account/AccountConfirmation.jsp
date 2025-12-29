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

<title>アカウント登録確認</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css">
<style>
h1 { font-size: 20px; margin-bottom: 16px; }
.row { margin: 8px 0; }
.label { display: inline-block; width: 160px; color: #555; }
.val { font-weight: normal; }
.actions { margin-top: 20px; display: flex; gap: 12px; }
button { padding: 6px 12px; }
</style>
</head>

<body style="text-align:center;">

<%
AccountForm form = (AccountForm) request.getAttribute("AccountForm");
String name = form != null ? form.getName() : "";
String id = form != null ? form.getId() : "";
String password = form != null ? form.getPassword() : "";
%>

<jsp:include page="/WEB-INF/pageHeader.jsp"/>
<h1>アカウント登録確認</h1>


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

<!-- 上記の内容で会員登録します。ボタン -->
<form method="post" action="<%= request.getContextPath() %>/public/AccountCompleteServlet">
<input type="hidden" name="name" value="<%= name %>" />
<input type="hidden" name="id" value="<%= id %>" />
<input type="hidden" name="password" value="<%= password %>" />

    <div class="actions" style="text-align:center; margin-top:20px; justify-content: center;">
        <button type="submit"
                style="background-color:darkgreen; color:white; font-weight:bold; border:none; padding:8px 16px;">
            上記の内容で会員登録します。
        </button>
    </div>
</form>

<!-- 最初から入力しなおす。ボタン -->
<form method="get" action="<%= request.getContextPath() %>/public/AccountInputInitServlet">
    <div class="actions" style="text-align:center; margin-top:20px; justify-content: center;">
        <button type="submit"
                style="background-color:white; color:black; font-weight:bold; border:1px solid black; padding:8px 16px;">
            最初から入力しなおす。
        </button>
    </div>
</form>
</body>
</html>



