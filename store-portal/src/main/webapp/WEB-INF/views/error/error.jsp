<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
	<title>系统繁忙</title>
	<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<legend><small>系统繁忙</small></legend>
	
	<div class="alert">
	  <a class="close" data-dismiss="alert">×</a>
	  <strong>警告！</strong> ${ex.message}
	</div>

	<p><a href="javascript:history.back()">返回上一页</a></p>
</body>
</html>
