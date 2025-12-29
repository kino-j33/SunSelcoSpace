<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jspHeader.jsp" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>予約状況確認</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/static/css/bookingStatus.css">
</head>

<body class="bg-color__light-gray-color">
  <jsp:include page="/WEB-INF/pageHeader.jsp" />
  <div class="wrap">
    <section class="inner bg-color__white-color">
      <h1 class="header--1">予約状況確認</h1>
      <jsp:include page="/WEB-INF/error_message_list.jsp" />
      <section>
        <h2>${SESSION_KEY_LOGIN_ACCOUNT.name}<span>さん</span>の予約一覧
        </h2>
        <c:if test="${empty bookingStatusList}">
          <p>現在予約がない状況です。</p>
        </c:if>


        <c:if test="${not empty bookingStatusList}">
          <form
            action="${pageContext.request.contextPath}/BookingCancelConfirmationServlet"
            method="get">
            <c:forEach var="bookingStatus" items="${bookingStatusList}">
              <div class="itemWrap card border__2-main-color">
                <div class="item--content">
                  <p class="room-name">${bookingStatus.roomName }</p>
                  <div class="content-wrap flex__row flex__gap">
                    <dl class="flex__row">
                      <dt>予約日：</dt>
                      <dd>${bookingStatus.bookingDate }</dd>
                    </dl>
                    <dl class="flex__row">
                      <dt>目的：</dt>
                      <dd>${bookingStatus.purpose }</dd>
                    </dl>
                  </div>
                </div>
                <!-- // item--content -->

                <div class="item--btn">
                  <button type="submit" name="bookingId"
                    value="${bookingStatus.id }"
                    class="btn font-color__white bg-color__color-main">キャンセル</button>
                </div>
                <!-- // item--btn -->

              </div>
            </c:forEach>
          </form>
        </c:if>
      </section>
    </section>
    <!-- // inner -->
  </div>
  <!-- // wrap -->
</body>

</html>