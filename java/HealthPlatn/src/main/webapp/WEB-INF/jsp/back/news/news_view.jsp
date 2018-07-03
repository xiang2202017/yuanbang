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
<%@ include file="../top.jsp"%>

</head>
<body>


	<div class="container-fluid" id="main-container">



		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="span12">
					<div class="widget-box">
						<div
							class="widget-header widget-header-blue widget-header-flat wi1dget-header-large"
							style="min-height:38px;height: 38px; line-height: 38px;padding-right: 8px">
							<h4 class="lighter">资讯详情</h4>
							<a class="lighter" style="cursor: pointer; padding-right: 8px;"
								onclick="returntoList()">返回</a>
						</div>
						<div class="step-content row-fluid position-relative"></div>
						<div class="widget-body">
<!-- 							<input id="contentInp" type="hidden" value="${pd.content}"> -->

							<div class="widget-main">
								<div class="step-content row-fluid position-relative">
										<div id="contentDiv">${pd.content}</div>
										<hr width="100%">
										
										<table style="width: 100%">
											<tr>
												<th width="20%" align="right">资讯分类</td>
												<td><c:if test="${pd.type == '1' }">
														<span class="label ">公司资讯</span>
													</c:if> <c:if test="${pd.type == '2' }">
														<span class="label ">健康资讯</span>
													</c:if>
												</td>
											</tr>
											<tr>
												<th align="right">作者</td>
												<td><span>${pd.author }</span>
												</td>
											</tr>
											<tr>
												<th align="right">关键字</td>
												<td><span>${pd.keywords }</span>
												</td>
											</tr>
											<tr>
												<th align="right">摘要</td>
												<td>${pd.remark }
												</td>
											</tr>
											<tr>
												<th align="right">创建人</td>
												<td>${pd.creator }
												</td>
											</tr>
											<c:if test="${pd.editor != null && pd.editor != ''}">
												<tr>
													<th align="right">修改人</td>
													<td>${pd.editor }
													</td>
												</tr>
												<tr>
													<th align="right">最后修改时间</td>
													<td>${pd.editime }
													</td>
												</tr>
											</c:if>
										</table>
									</div>



								</div>
							</div>
							<!--/widget-main-->
						</div>
						<!--/widget-body-->
					</div>
				</div>



				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	<!--/.fluid-container#main-container-->

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i> </a>

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

		$(function() {
// 			var content = $("#contentInp").val();
// 			alert(content);
			//$("#contentDiv").html(content);
		});

		// 			uParse('#contentDiv', {
		//     			rootPath: '../'
		//     		});

		function returntoList() {
			history.back();
		}
	</script>

</body>
</html>

