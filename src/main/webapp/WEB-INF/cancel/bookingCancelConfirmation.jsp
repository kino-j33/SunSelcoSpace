<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jspHeader.jsp" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>キャンセル確認</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/static/css/cancel.css">
</head>

<body class="bg-color__light-gray-color">
  <jsp:include page="/WEB-INF/pageHeader.jsp" />
  <div class="wrap">
    <div class="inner bg-color__white-color">
      <h1 class="header--1">キャンセル確認</h1>
      <c:if test="${not empty ERROR_MESSAGE_LIST}">
        <ul class="font-color__orange mt-50">
          <c:forEach var="message" items="${ERROR_MESSAGE_LIST}">
            <li><c:out value="${message}" /></li>
          </c:forEach>
        </ul>
      </c:if>

      <div
        class="card mt-35 border__2-main-color bg-color__light-gray-color">
        <form
          action="${pageContext.request.contextPath}/BookingCancelCompleteServlet"
          method="post">
          <dl class="pair-list">
            <dt>
              <p>施設名</p>
            </dt>
            <dd>
              <p>${roomEntity.name}</p>
              <input type="hidden" name="name"
                value="${roomEntity.name}">
            </dd>

            <dt>
              <p>場所</p>
            </dt>
            <dd>
              <p>${roomEntity.location}</p>
              <input type="hidden" name="location"
                value="${roomEntity.location}">
            </dd>

            <dt>
              <p>予約日</p>
            </dt>
            <dd>
              <p>${bookingEntity.bookingDate}</p>
              <input type="hidden" name="date"
                value="${bookingEntity.bookingDate}">
            </dd>

            <dt>
              <p>料金</p>
            </dt>
            <dd>
              <p>
                <fmt:formatNumber value="${roomEntity.fee}"
                  type="number" />
                円
              </p>
              <input type="hidden" name="fee" value="${roomEntity.fee}">
            </dd>

            <dt>
              <p>利用目的</p>
            </dt>
            <dd>
              <p>${bookingEntity.purpose}</p>
              <input type="hidden" name="purpose"
                value="${bookingEntity.purpose}">
            </dd>
          </dl>

          <div class="img_wrap mt-45">
            <div class="img_container">
              <img src="<c:url value='${roomEntity.image}'/>"
                alt="${roomEntity.name}"
                onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/static/img/noimage.png';" />
            </div>
          </div>

          <input type="hidden" name="id" value="${bookingEntity.id}">

          <div class="btn-wrap  mt-70">
            <a
              href="${pageContext.request.contextPath}/BookingStatusInitServlet"
              class="btn btn01 font-color__white bg-color__color-main">予約一覧へ戻る</a>
            <button type="submit"
              class="btn btn01 font-color__white bg-color__color-main">キャンセル確定</button>
          </div>
        </form>
      </div>
      <!-- // card -->
    </div>
    <!-- // inner -->
  </div>
  <!-- // wrap -->
</body>

</html>