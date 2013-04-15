<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>未处理订单</title>
	<script  type="text/javascript">
	
	$(function() {
   		// Loading 按钮
		$('#loadingDiv')
		.hide()
		.ajaxStart(function() {
		    $(this).show();
		})
		.ajaxStop(function() {
		    $(this).hide();
		});
	});
	
	function fetchTrade(day) {
		var action = "${ctx}/trade/waits/fetch?preday=" + day;
		htmlobj=$.ajax({
			url:action,
			async:true,
			type:"post",
			success: function(msg) {
               $("#fetchBody").html(htmlobj.responseText);
            },
			error: function(XMLHttpRequest, textStatus, errorThrown) {
            }
		});
	}
	
	
	</script>
</head>
<body>
	
    <div class="btn-group">
      <button class="btn dropdown-toggle btn-primary" data-toggle="dropdown">淘宝订单抓取<span class="caret"></span></button>
      <ul class="dropdown-menu">
        <li><a href="javascript:fetchTrade(0)">今天</a></li>
        <li><a href="javascript:fetchTrade(1)">昨天</a></li>
        <li><a href="javascript:fetchTrade(2)">前天</a></li>
      </ul>
    </div><!-- /btn-group --> 
     
	<div id="fetchBody">
	</div>
	
	<div id="loadingDiv" class="hint">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>	
   
</body>
</html>
