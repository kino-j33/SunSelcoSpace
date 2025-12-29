<%@
  page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jspHeader.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>キャンセル完了</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>

<body class="bg-color__light-gray-color">
  <jsp:include page="/WEB-INF/pageHeader.jsp" />
  <div class="wrap">
    <div class="inner bg-color__white-color">
      <h1 class="header--1">キャンセル完了</h1>
      <div
        class="card mt-35 border__2-main-color bg-color__light-gray-color">
        <dl class="pair-list">
          <dt>
            <p>施設名</p>
          </dt>
          <dd>
            <p>
              <c:out value="${bookingForm.name}" />
            </p>
          </dd>

          <dt>
            <p>場所</p>
          </dt>
          <dd>
            <p>
              <c:out value="${bookingForm.location}" />
            </p>
          </dd>

          <dt>
            <p>予約日</p>
          </dt>
          <dd>
            <p>
              <c:out value="" />${bookingForm.bookingDate}</p>
          </dd>

          <dt>
            <p>料金</p>
          </dt>
          <dd>
            <p>
              <fmt:formatNumber value="${bookingForm.fee}"
                pattern="#,##0" />
              円
            </p>
          </dd>

          <dt>
            <p>利用目的</p>
          </dt>
          <dd>
            <p>
              <c:out value="${bookingForm.purpose}" />
            </p>
          </dd>
        </dl>
      </div>
      <!-- // card -->
    </div>
    <!-- // inner -->
  </div>
  <!-- // wrap -->
</body>

</html>