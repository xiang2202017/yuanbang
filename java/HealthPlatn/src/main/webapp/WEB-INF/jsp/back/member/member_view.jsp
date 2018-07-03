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
							<h4 class="lighter">会员详情</h4>
							<a class="lighter" style="cursor: pointer; padding-right: 8px;"
								onclick="returntoList()">返回</a>
						</div>
						<div class="step-content row-fluid position-relative"></div>
						<div class="widget-body">

								<div class="widget-main">
								<div class="step-content row-fluid position-relative">
										<table style="width: 100%">
												<tr>
													<th align="right">会员编号</th>
													<td><span>${pd.memberNo }</span>
												</tr>
												<tr>
													<th align="right">会员姓名</th>
													<td><span>${pd.memberName }</span>
												</tr>
												
												<tr>
													<th align="right">性别</th>
													<td>
													<c:if test="${pd.sex == '1' }"> 
														<span class="label ">男</span>
													</c:if> <c:if test="${pd.sex == '2' }">
														<span class="label ">女</span>
													</c:if>
												</td>
												</tr>
												
												<tr>
													<th align="right">密码</th>
													<td><span>${pd.password }</span>
												</tr>
												
												<tr>
													<th width="20%" align="right">会员分类</th>
													<td><span>${pd.memberTypeName }</span>
												</tr>
												
												<tr>
													<th align="right">身份证号</th>
													<td><span>${pd.idcardNo }</span>
												</tr>
												<tr>
													<th align="right">手机号码</th>
													<td><span>${pd.phone }</span>
												</tr>
												<tr>
													<th align="right">所属分公司</th>
													<td><span>${pd.company }</span>
												</tr>
												
												<tr>
													<th align="right">期限</th>
													<td><span>${pd.period }</span>
												</tr>
												
											</table>
									<h4 class="lighter">会员收货地址</h4>
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>收货人</th>
												<th>手机</th>
												<th>地址</th>
												<th>邮编</th>
												<th>是否默认</th>
											</tr>
										</thead>
										<tbody>
										<c:choose>
											<c:when test="${not empty memberAddressList}">
												<c:forEach items="${memberAddressList}" var="item" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td>${item.name }</td>
														<td>${item.phone }</td>
														<td>${item.address }</td>
														<td>${item.postCode }</td>
														<td>
															<c:if test="${item.isDefault == '1' }">是</c:if>
															<c:if test="${item.isDefault == '2' }">否</c:if>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center">该会员还没有添加收货地址！</td>
												</tr>
											</c:otherwise>
										</c:choose>
										</tbody>
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

		function returntoList() {
			history.back();
		}
	</script>

</body>
</html>

