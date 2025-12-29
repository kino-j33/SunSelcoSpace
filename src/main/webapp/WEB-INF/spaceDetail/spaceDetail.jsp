<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="jakarta.tags.core" %>
	<%@ include file="/WEB-INF/jspHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>施設詳細画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/spaceDetail.css">

</head>
<body>
<jsp:include page="/WEB-INF/pageHeader.jsp"/>

  <div class="info-all">
  <div class="info-contents">
  <h1>施設詳細</h1>
    <img
        src="${pageContext.request.contextPath}/${roomEntity.image}"
        alt="<c:out value='${roomEntity.name}'/>"
        onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/static/img/noimage.png';">

</div>
	<div class="info-list">
	<div class="info-row">
		<span class="label">施設名：</span>
		<span>${roomEntity.name}</span>
	</div>
	 <div class="info-row">
      <span class="label">場所：</span>
      <span>${roomEntity.location}</span>
   </div>
    <div class="info-row">
        <span class="label">人数：</span>
        <span>${roomEntity.capacity} 人</span>
    </div>
    <div class="info-row">
        <span class="label">料金：</span>
        <span>${roomEntity.fee} 円</span>
     </div>
     <div class="info-row">
        <span class="label">紹介文：</span><br>
        <span>${roomEntity.introduction}</span>
    </div>
    <form class="booking-formaction" action="${pageContext.request.contextPath}/BookingInputInitServlet" method="post">
      <div>
	    <input type="hidden" name="id" value="${roomEntity.id}">
	    <button type="submit">予約する</button>
	    </div>
      </div>
	 </form>
	</div>
</body>
</html>
<!--  http://localhost:8080/SunSelcoSpace/accountLogin.jsp -->