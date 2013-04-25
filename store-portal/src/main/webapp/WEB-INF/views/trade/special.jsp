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
	
	function delayEnable(time){
	    var hander = setInterval(function () {
	    	time--;
        if (time > 0) {
        	$('#info').text('重新抓单需等待' +time+'秒');
        } else {
        	clearInterval(hander);
        	$('#info').text(' 抓取未处理交易订单（买家已付款）');
	        $('.btn').attr('disabled',false);
        }
	    }, 1000);	
	}
	
	function fetchTrade(day) {
		$('.btn').attr('disabled',true);
		delayEnable(10);
		var action = "${ctx}/trade/special/fetch/ajax?preday=" + day;
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
	
	<legend><small>活动特卖专场 用于大批量交易订单处理</small></legend>
	<div>
		<div class="row">
		  <div class="span2">
		      <div class="btn-group" data-toggle="buttons-radio">
		      	  <button onclick="javascript:fetchTrade(0)" class="btn btn-primary">淘宝交易抓单</button>
			  </div>	  
		  </div>
		  <div id="info" class="span4 alert alert-info">
			  抓取未处理交易订单（买家已付款）
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
