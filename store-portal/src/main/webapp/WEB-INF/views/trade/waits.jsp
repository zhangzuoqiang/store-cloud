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
	
	<div class="row">
	  <div class="span4">
	      <div class="btn-group" data-toggle="buttons-radio">
		  <button onclick="javascript:fetchTrade(0)" class="btn btn-info">今天</button>
		  <button onclick="javascript:fetchTrade(1)" class="btn btn-info">昨天</button>
		  <button onclick="javascript:fetchTrade(2)" class="btn btn-info">前天</button>
		  </div>	  
	  </div>
	</div>

	<div id="fetchBody">
	</div>
	
	<div id="loadingDiv" class="hint">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>

</body>
</html>
