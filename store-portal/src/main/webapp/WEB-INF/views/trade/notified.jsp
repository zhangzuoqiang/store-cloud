<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

	<c:if test="${not empty errors}">
		<div id="message" class="alert alert-success">
			<c:forEach items="${errors}" var="error">
				${error} <br>
			</c:forEach>
		</div>
	</c:if>
	
	<c:if test="${empty errors}">
		通知成功!
	</c:if>
