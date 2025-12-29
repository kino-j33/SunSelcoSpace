<%@
	page language="java"
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ include file="/WEB-INF/jspHeader.jsp" %>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>予約入力</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body class="bg-color__light-gray-color">
<jsp:include page="/WEB-INF/pageHeader.jsp"/>
	<div class="wrap">
		<div class="inner bg-color__white-color">
			<h1 class="header--1">予約入力</h1>
			<c:if test="${not empty ERROR_MESSAGE_LIST}">
				<ul class="font-color__orange mt-50">
					<c:forEach var="message" items="${ERROR_MESSAGE_LIST}">
						<li><c:out value="${message}"/></li>
					</c:forEach>
				</ul>
			 </c:if>

			<div class="card mt-35 border__2-main-color bg-color__light-gray-color">
				<form action="${pageContext.request.contextPath}/BookingConfirmationServlet" method="post">
					<dl class="pair-list">

						<dt>
							<p>施設名</p>
						</dt>
						<dd>
							<p>${roomEntity.name}</p>
						</dd>

						<dt>
							<p>場所</p>
						</dt>
						<dd>
							<p>${roomEntity.location}</p>
						</dd>

						<dt>
							<p>人数</p>
						</dt>
						<dd>
							<p>${roomEntity.capacity}人</p>
						</dd>

						<dt>
							<p>料金</p>
						</dt>
						<dd>
							<p>${roomEntity.fee}円</p>
						</dd>

						<dt>
							<p>利用できない日</p>
						</dt>
						<dd>
							<c:if test="${empty bookedDateList}">
						        <p>利用できない日はありません</p>
						    </c:if>
						    
							<c:forEach var="date" items="${bookedDateList}">
								<div>
						            <c:out value="${date}" />
						        </div>
							</c:forEach>
						</dd>

						<dt>
							<p>予約日</p>
						</dt>
						<dd>
							<p><input type="date" name="bookingDate" value="${bookingForm.bookingDate}"></p>
						</dd>

						<dt>
							<p>利用目的</p>
						</dt>
						<dd>
							<p><input type="text" name="purpose" value="${bookingForm.purpose}"></p>
						</dd>

						<dt>
							<p>利用規約に同意</p>
						</dt>
						<dd>
							<p><input type="checkbox" name="agree" value="true">同意する</p>
						</dd>

					</dl>
					<input type="hidden" name="id" value="${bookingForm.roomId}">

					<div class="btn-wrap mt-40">
						<button type="submit"
							class="btn01 font-color__white bg-color__color-main">予約確認へ進む</button>
					</div>
				</form>
			</div><!-- // card -->
		</div><!-- // inner -->
	</div><!-- // wrap -->
</body>
</html>