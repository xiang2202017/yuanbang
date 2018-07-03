<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	
</script>
<div style="float: left;">
	<table>
		<tr>
			<td>起始时间：<input class="span10 date-picker" name="fromTime" id="fromTime"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:100px;" placeholder="日期起始"/></td>
			<td>终止时间：<input class="span10 date-picker" name="toTime" id="toTime"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:100px;" placeholder="日期终止"/></td>
			<td><button onclick="searchMoney()">搜索</button></td>
		</tr>
	</table>
</div>
<hr width="100%">
<div id="achieveDataDiv">
	
	
</div>

