<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Flat UI</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Loading Bootstrap -->
    <link href="${ctx}/static/flat-ui/css/bootstrap.css" rel="stylesheet">
    <!-- Loading Flat UI -->
    <link href="${ctx}/static/flat-ui/css/flat-ui.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
    <!--[if lt IE 9]>
      <script src="${ctx}/static/flat-ui/js/html5shiv.js"></script>
    <![endif]-->
	
  </head>
  <body>
 	<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal login-form">
    <div class="container">
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
	
    	<div class="login">
        <div class="login-screen">
          <div class="login-icon">
            <h4>欢迎访问 <small>仓储云后台</small></h4>
          </div>

          <div class="login-form">
            <div class="control-group">
              <select id="centro_select" class="span3">
              		<option value="1">湘潭高新仓</optin>
              		<option value="2">郴州2号仓</optin>
              </select>
            </div>
            
            <div class="control-group">
              <input type="text" class="login-field" placeholder="输入账号" id="login-name" name="username"/>
              <label class="login-field-icon fui-man-16" for="login-name"></label>
            </div>

            <div class="control-group">
              <input type="password" class="login-field" value="" placeholder="输入密码" id="login-pass" name="password"/>
              <label class="login-field-icon fui-lock-16" for="login-pass"></label>
            </div>
            <input type="submit" class="btn btn-primary btn-large btn-block" value=" 登 录 ">
          </div>
        </div>
      </div>
 	</div>
	</form>
    <!-- Load JS here for greater good =============================-->
    <script src="${ctx}/static/flat-ui/js/jquery-1.8.2.min.js"></script>
    <script src="${ctx}/static/flat-ui/js/jquery.dropkick-1.0.0.js"></script>
    <script src="${ctx}/static/flat-ui/js/application.js"></script>
    <!--[if lt IE 8]>
      <script src="${ctx}/static/flat-ui/js/icon-font-ie7.js"></script>
      <script src="${ctx}/static/flat-ui/js/icon-font-ie7-24.js"></script>
    <![endif]-->
  </body>
</html>
