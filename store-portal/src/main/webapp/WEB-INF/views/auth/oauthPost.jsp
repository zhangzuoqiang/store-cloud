<%@ page contentType="text/html;charset=UTF-8"%>
<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.css" type="text/css" rel="stylesheet" />
<html>
<head/>

<script>
	function forward() {
		window.setTimeout(
		function(){
		  document.loginform.submit();
		},800);
	}
	
	
</script>
 
<body onload="javascript:forward()">
	<div class="container">
	  <div class="inner">
	    <p>欢迎访问云端仓储物流配送中心, 正在进行安全检查 ...</p>
	    <form name="loginform" action="https://oauth.tbsandbox.com/token" method="post">
			<input type="hidden" name="code" value="${code}">
			<input type="hidden" name="client_id" value="1021474419">
			<input type="hidden" name="client_secret" value="sandboxc6fda58609e29306a947fefc4">
			<input type="hidden" name="grant_type" value="authorization_code">
			<input type="hidden" name="redirect_uri" value="http://www.wlpost.com/top_oauth_get">
		</form>
	  </div>
	</div>

 </body>
</html>