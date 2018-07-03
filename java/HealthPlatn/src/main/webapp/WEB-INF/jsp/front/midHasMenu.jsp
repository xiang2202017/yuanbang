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
	
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />	
</head>
<body>

	<div class="container-fluid" id="main-container">
		<!-- menu toggler -->
<%--nav nav-pills nav-stacked--%>
		<div id="sidebar" class="menu-min">

				<ul class="nav nav-list">

					<c:forEach items="${menuList}" var="menu">
						<li id="${menu.MENU_ID }">
							  <a style="cursor:pointer;" target="rightFrame"  >
								<span>${menu.MENU_NAME }</span>
							  </a>
							  <ul class="submenu">
									<c:forEach items="${menu.subMenu}" var="sub">
										<c:if test="${not empty sub.MENU_URL}">
											<li id="z${sub.MENU_ID }">
												<a style="cursor:pointer;" target="rightFrame"  >${sub.MENU_NAME }</a>
											</li>
										</c:if>
									</c:forEach>
						  		</ul>
						</li>
					</c:forEach>

				</ul><!--/.nav-list-->

			</div><!--/#sidebar-->
		

		<div id="main-content" class="clearfix">

			<div>
				<iframe name="rightFrame" id="rightFrame" frameborder="0" src="#" style="margin:0 auto;width:100%;height:100%;"></iframe>
			</div>

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	
	
	<!-- basic scripts -->
		<!-- 引入 -->
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/myjs/menusf.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/index.js"></script>
</body>
</html>
