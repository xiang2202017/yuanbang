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
							<h4 class="lighter">消息详情</h4>
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
												<th align="right">针对会员类型</th>
												<td>
												<span>
													<c:if test="${pd.toType == 0 }">
														所有会员
													</c:if>
													<c:if test="${pd.toType == 1 }">
														优惠会员
													</c:if>
													<c:if test="${pd.toType == 2 }">
														经销会员
													</c:if>
													<c:if test="${pd.toType == 3 }">
														特定会员
													</c:if>
												</span>
												</td>
											</tr>
											<c:if test="${pd.memberNos != null && pd.memberNos != '' }">
												<th align="right">特定会员号</th>
												<td>
												<span>
													${pd.memberNos }
												</span>
												</td>
											</c:if>
											<tr>
												<th align="right">摘要</th>
												<td>${pd.remark }
												</td>
											</tr>
											<tr>
												<th align="right">创建人</th>
												<td>${pd.creator }
												</td>
											</tr>
											<tr>
												<th align="right">创建时间</th>
												<td>${pd.creatime }
												</td>
											</tr>
											<c:if test="${pd.editor != null && pd.editor != ''}">
												<tr>
													<th align="right">修改人</th>
													<td>${pd.editor }
													</td>
												</tr>
												<tr>
													<th align="right">最后修改时间</th>
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

