<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	//返回
	function logisticsBack(){
		$("#logisticsBack").attr("disabled", true);
		lastMemberPage.pop();
		$("#listDiv").html(lastMemberPage[lastMemberPage.length - 1]);
	}

</script>
	<div style="padding-bottom: 10px">
	<div style="float: left;"><font style="font-weight: bold;" size="4px">物流跟踪</font></div>
	<div style="float: right;"><a class="button" onclick="logisticsBack()" id="logisticsBack">返回</a></div>
</div>
	
	<table style="width: 100%">
		<tr>
			<td></td>
			<td align="right"><font style="font-weight: bold;color: orange;">${logistics.typeName }</font></td>
			<td><font style="font-weight: bold;color: orange;">${logistics.logisticsNo }</font>
			</td>
		</tr>
		<c:forEach items="${logistics.traceList }" var="item" varStatus="vs">
			<tr>
				<td>
				<c:if test="${vs.index != 0 }">
					<img alt="" src="<%=basePath%>static/img/arrow.jpg" width="20" height="15">
				</c:if>
				</td>
				<td>${item.AcceptTime }</td>
				<td>${item.AcceptStation }</td>
			</tr>
		</c:forEach>
	</table>
