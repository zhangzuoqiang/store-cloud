<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div id="header">
<div class="navbar  navbar-fixed-top">
    <div class="navbar-inner">
        <div class="nav-collapse">
          <ul class="nav">
            <li><a  href="#"> &nbsp;&nbsp;<strong>物流通</strong>&nbsp;电商仓储配送中心</a></li>
            <li></li>
            <li class="active"><a href="#"><i class="icon-user"></i>&nbsp;<shiro:principal property="shopname"/></a></li>
          </ul>
          <ul class="nav">
            
          </ul>          
          <ul class="nav pull-right">
          	<li><a href="#"><i class="icon-comment"></i>消息</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog"></i>功能 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">设置</a></li>
                <li class="divider"></li>
                <li><a href="#">退出</a></li>
              </ul>
            </li>
          </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /navbar-inner -->
  </div><!-- /navbar -->
 	
  <legend></legend> 
  <br><br>
  	<!-- <img src = "${ctx}/static/images/envato.png"> 
	<h1 class="small">
	&nbsp;&nbsp;
    &nbsp;&nbsp;物流通&nbsp; <shiro:principal property="shopname"/>
    </h1>
    -->
</div>