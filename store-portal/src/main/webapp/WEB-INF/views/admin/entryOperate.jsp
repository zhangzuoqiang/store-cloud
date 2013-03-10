<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>处理入库单</title>
	<script>
		
		// 刷新良品数量		
		function reflush(id) {
			// 错误总数
			errs = 0;
			errs += Number($('#err1_input_' + id).val());
			errs += Number($('#err2_input_' + id).val());
			errs += Number($('#err3_input_' + id).val());
			// ok_input控件
			oldNum = Number($('#ok_base_' + id).val());
			newNum = oldNum-errs;
			$('#ok_input_' + id).val(newNum);
		}
				
		$(document).ready(function() {
			$(".err").keyup(function() {
				id = $(this).attr('flag');
				reflush(id);
			});
		});
				
	</script>	
</head>

<body>
	<legend></legend>
	<table class="table btn-info">
		<tr>
			<td><strong>入库单单号</strong>：${order.orderno}</td>
			<td><strong>入库仓库</strong>：湘潭高新仓</td>
			<td><strong>总件数</strong>：${order.totalnum}</td>
		</tr>
		<tr>
			<td><strong>运输公司运单号</strong>：${order.expressOrderno}</td>
			<td><strong>运输公司名称</strong>： ${order.expressCompany}</td>
			<td><strong>送货详细地址</strong>：${order.receiverAddress}</td>
		</tr>		
	</table>	
	
	<form id="operateForm" action="${ctx}/admin/entry/operate" method="post">
	<input type="hidden" name="content['centro_id']" value="${order.centroId}"/>
	<input type="hidden" name="content['user_id']" value="${order.createUser.id}"/>
	<input type="hidden" name="content['order_id']" value="${order.id}"/>
	<table id="contentTable" class="table table-condensed">
		<thead><tr>
		<th>商品名称</th><th>商品编号</th>
		<th>计费重量(克)</th><th>总件数</th><th>入库处理</th></tr></thead>
		<tbody>
		<c:forEach items="${order.details}" var="detail">
			<tr>
				<td class="style1">${detail.item.title}</td>
				<td>${detail.item.code}</td>
				<td>${detail.item.weight}</td>
				<td>${detail.num}</td>
				<td>
					<input type="hidden" id="item_id" name="content['item_${detail.item.id}']" value="${item.id}"/>
					<input type="hidden" id="ok_base_${detail.item.id}" value="${detail.num}"/>
					<div class="input-prepend input-append">
					<span class="add-on">良品</span><input name="content['ok_${detail.item.id}']" id="ok_input_${detail.item.id}"  class="ok span2 disabled" size="10" type="text" value="${detail.num}" readOnly/></span>
               	  	<span class="add-on">残次</span><input name="content['err1_${detail.item.id}']" id="err1_input_${detail.item.id}" flag="${detail.item.id}" class="err span2" size="10" type="text" placeholder="输入残品数量.." value="0"></span>
               	  	<span class="add-on">机损</span><input name="content['err2_${detail.item.id}']" id="err2_input_${detail.item.id}" flag="${detail.item.id}" class="err span2" size="10" type="text" placeholder="输入机损数量.." value="0"></span>
               	  	<span class="add-on">箱损</span><input name="content['err3_${detail.item.id}']" id="err3_input_${detail.item.id}" flag="${detail.item.id}" class="err span2" size="10" type="text" placeholder="输入箱损数量.." value="0"></span>
					</div>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="form-actions"  style="text-align: right;">
		<input id="submit_btn" class="btn btn-primary" type="submit" value="确认入库"/>&nbsp;
		<input id="cancel_btn" class="btn" type="button" value="暂不处理" onclick=""/>
	</div>
	</form>
</body>
</html>
