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
        	$('#info').text(' 抓取未处理交易订单（买家已付款等待商家发货）');
	        $('.btn').attr('disabled',false);
        }
	    }, 1000);	
	}
	
	function fetchTrade(day) {
		$('.btn').attr('disabled',true);
		delayEnable(60);
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
	  <div class="span2">
	      <div class="btn-group" data-toggle="buttons-radio">
			  <button onclick="javascript:fetchTrade(0)" class="btn btn-info"> 今 天 </button>
			  <button onclick="javascript:fetchTrade(1)" class="btn btn-info"> 昨 天 </button>
			  <button onclick="javascript:fetchTrade(2)" class="btn btn-info"> 前 天 </button>
		  </div>	  
	  </div>
	  <div id="info" class="span4 alert alert-info">
		  抓取未处理交易订单（买家已付款等待商家发货）
	  </div>
	</div>

	<div id="fetchBody">
	</div>
	
	<div id="loadingDiv" class="hint">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>

</body>
</html>
