<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>待处理出库单</title>
</head>

<body>
	<br>
	共${waits.size}条待处理出库单<br>
	<c:forEach items="${waits.keySet}" var="key">
		${key} :
		<%= waits%>
	</c:forEach>
</body>
</html>
