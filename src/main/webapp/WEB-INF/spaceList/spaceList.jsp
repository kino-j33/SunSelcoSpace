<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jspHeader.jspに共通を格納 -->
<%@ include file="/WEB-INF/jspHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>施設一覧</title>
<!-- CSSの読み込み -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/spaceListStyle.css">
</head>
<body>
<jsp:include page="/WEB-INF/pageHeader.jsp"/>
	<div class="wrap">
		<h2>施設一覧</h2>
		<!-- JSTLコアタグを使ったループ -->
		<c:forEach var="room" items="${roomList}">
			<div class="row">
				<!-- 画像の表示 -->
				<img
					src="${pageContext.request.contextPath}/${room.image}"
					alt="${room.name}"
					onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/static/img/noimage.png';"
				/>

				<!-- 施設情報(RoomEntity)の表示 -->
				<div class="info-box">
					<h2>${room.name}</h2>
					<p><strong>住所：</strong> ${room.location}</p>
					<p><strong>人数：</strong> ${room.capacity}人</p>
					<p><strong>概要：</strong> ${room.overview}</p>
					<p><strong>料金：</strong> ${room.fee}円</p>

					<!-- 詳細ボタン＆room-idをformで送信 -->
					<form action="${pageContext.request.contextPath}/public/SpaceDetailInitServlet" method="get">
						<input type="hidden" name="room-id" value="${room.id}">
						<button type="submit">詳細を見る</button>
					</form>
				</div>
			</div>
		</c:forEach>

		<!-- 注意事項 -->
		<p class="notice">
		<small>※当レンタルスペースは、異世界の時間軸、異種族間での認識の違いなどを考慮し、
		時間単位ではなく1日単位での貸し出しとなります。
		<br>予めご了承ください。</small></p>
	</div>
</body>
</html>