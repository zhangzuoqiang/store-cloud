<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>待处理出库单</title>
</head>

<body>
	<br>
	<% com.graby.store.base.GroupMap results = (com.graby.store.base.GroupMap)request.getAttribute("waits"); %>
	共${waits.size}条待处理出库单.<br>
	
	<ul id="tab" class="nav nav-tabs">
	<c:forEach items="${waits.keySet}" var="key" varStatus="i">
		<li <c:if test="${i.index==0}">	class="active"</c:if>><a href="${key}" data-toggle="tab">${key}</a></li>
	</c:forEach>
	</ul>
	
</body>
</html>
