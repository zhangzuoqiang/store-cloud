<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div id="header">
	<h1 class="small">
	&nbsp;&nbsp;<img src = "${ctx}/static/images/package.png">
    &nbsp;&nbsp;物流通&nbsp; <shiro:principal property="shopname"/>
    </h1>
</div>