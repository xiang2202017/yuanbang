<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
</head>
<body>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.7.2.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- 引入 -->

<!-- 	<script type="text/javascript" charset="utf-8" -->
<!-- 		src="plugins/ueditor/ueditor.parse.js"></script> -->

	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--引入属于此页面的js -->
	<script type="text/javascript" src="static/js/myjs/image.js"></script>
	<script type="text/javascript">
		$(top.hangge());
	</script>

</body>
<div id="zhongxin">
	<table style="width: 100%">
		<tr>
			<td align="right"><font style="font-weight: bold;color: orange;">${logistics.typeName }</font></td>
			<td><font style="font-weight: bold;color: orange;">${logistics.logisticsNo }</font>
			</td>
		</tr>
		<c:forEach items="${logistics.traceList }" var="item" varStatus="vs">
			<c:if test="${vs.index != 0 }">
				<tr align="center" height="40">
				<td colspan="2"><img alt="" src="<%=basePath%>static/img/arrow.jpg" width="20" height="15"> </td>
				</tr>
			</c:if>
			<tr>
				<td>${item.AcceptTime }</td>
				<td>${item.AcceptStation }</td>
			</tr>
		</c:forEach>
	</table>
</div>

</html>

