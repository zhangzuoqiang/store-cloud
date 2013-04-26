<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>登录页</title>
	<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/flat-ui/css/flat-ui.css" type="text/css" rel="stylesheet" />
	 
	<script>
		$(document).ready(function() {
			$("#username").focus();
			$("#loginForm").validate();
		});
	</script>
</head>

	<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal login-form">
	<%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
		</div>
	<%
	}
	%>
	<fieldset>
		<legend><small>物流通后台登陆页面</small></legend>
		
		<div class="control-group">
			<label for="centro" class="control-label">仓库选择</label>
			<div class="controls">
				<select name="centro">
				<c:forEach items="${centros}" var="centro">
					<option value="${centro.id}">${centro.name}</option>
				</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label for="username" class="control-label">名称:</label>
			<div class="controls">
				<input type="text" id="username" name="username"  value="${username}" class="login-field required" value="" placeholder="Enter your name" />
				<label class="login-field-icon fui-man-16" for="login-name"></label>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码:</label>
			<div class="controls">
				<input type="password" id="password" name="password" class="input-large login-field required"/>
				 <label class="login-field-icon fui-lock-16" for="login-pass"></label>
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
				<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
				<input id="submit_btn" class="btn btn-primary " type="submit" value="登录"/>
			 	<span class="help-block">(管理员: <b>admin/admin</b>)</span>
			</div>
		</div>
	</fieldset>
	</form>

  <body>
  
</html>
