<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>未处理订单</title>
	<script  type="text/javascript">
	
	function delayEnable(time){
	    var hander = setInterval(function () {
	    	time--;
	        if (time > 0) {
	        	$('#fetch').text('重新抓单需等待(' +time+'秒)');
	        } else {
	        	$('#fetch').text('一键抓取淘宝订单');
	        	$('#fetch').attr('disabled',false);
	        	clearInterval(hander);
	        }
	    }, 1000);	
	}
			
	$(function() {
		// 发送事件
		$('#fetch').bind('click', function (e) {
			$('#fetch').attr('disabled',true);
			delayEnable(20);
			// ajax action
			var action = "${ctx}/trade/waits/fetch";
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
			
	    });
	   
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
	
	
	</script>
</head>
<body>
	
	<legend></legend>
  	<div class="span4">
  		<a id="fetch" href="#" class="btn btn-primary"><i class="icon-inbox icon-white"></i> 一键抓取淘宝订单</a>
  	</div>
  	
	<div id="fetchBody">
		<div id="loadingDiv">
			<img src = "${ctx}/static/images/fetch.gif">
		</div>
	</div>
   
</body>
</html>
