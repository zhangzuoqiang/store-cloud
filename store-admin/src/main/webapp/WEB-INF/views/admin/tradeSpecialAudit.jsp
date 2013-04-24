<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>交易订单审核</title>
	<script type="text/javascript">
	</script>	
</head>

<body>
	<legend><small>活动专场 大批量交易订单审核</small></legend>
	<a class="btn btn-primary" href="${ctx}/trade/mkship/all">批量审核</a>
</body>
</html>
