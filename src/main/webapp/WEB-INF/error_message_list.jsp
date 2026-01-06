<%@ include file="/WEB-INF/jspHeader.jsp" %>

<c:if test="${not empty ERROR_MESSAGE_LIST}">
	<ul class="error-message font-color__orange mt-50">
		<c:forEach var="message" items="${ERROR_MESSAGE_LIST}">
			<li><c:out value="${message}"/></li>
		</c:forEach>
	</ul>
 </c:if>