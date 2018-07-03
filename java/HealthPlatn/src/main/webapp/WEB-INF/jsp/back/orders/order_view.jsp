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
							<h4 class="lighter">订单详情</h4>
							<a class="lighter" style="cursor: pointer; padding-right: 8px;"
								onclick="returntoList()">返回</a>
						</div>
						<div class="step-content row-fluid position-relative"></div>
						<div class="widget-body">
<!-- 							<input id="contentInp" type="hidden" value="${order.content}"> -->

							<div class="widget-main">
								<div class="step-content row-fluid position-relative">
										<table style="width: 100%">
											<tr>
												<th align="right">订单编号</th>
												<td><span>${order.orderNo }</span>
												</td>
											</tr>
											<tr>
												<th align="right">订单创建时间</th>
												<td><span>${order.createTime }</span>
												</td>
											</tr>
											<tr>
												<th width="20%" align="right">订单状态</th>
												<td>
													<c:if test="${order.status == 1 }">
														<span class="label "> 待付款</span>
													</c:if> 
													<c:if test="${order.status == 2 }">
														<span class="label ">待发货</span>
													</c:if>
													<c:if test="${order.status == 3 }">
														<span class="label ">待收货</span>
													</c:if>
													<c:if test="${order.status == 4 }">
														<span class="label ">交易完成</span>
													</c:if>
													<c:if test="${order.status == 5 }">
														<span class="label ">订单已关闭</span>
													</c:if>
												</td>
											</tr>
											<tr>
												<th align="right">会员号</th>
												<td><span>${order.memberNo }</span>
												</td>
											</tr>
											<tr>
												<th align="right">会员名</th>
												<td><span>${order.memberName }</span>
												</td>
											</tr>
											<tr>
												<th align="right">订单金额</th>
												<td><span>${order.money }</span>
												</td>
											</tr>
											<c:if test="${order.type != null }">
												<tr>
													<th align="right">支付类型</th>
													<td>
														<c:if test="${order.payType == 1 }">
															<span class="label ">支付宝</span>
														</c:if>
														<c:if test="${order.payType == 2 }">
															<span class="label ">微信</span>
														</c:if>
														<c:if test="${order.payType == 3 }">
															<span class="label ">银联</span>
														</c:if>
													</td>
												</tr>
												<tr>
													<th align="right">支付流水号</th>
													<td>${order.payNo }
													</td>
												</tr>
												<tr>
													<th align="right">支付时间</th>
													<td>${order.payTime }
													</td>
												</tr>
											</c:if>
											<tr>
												<th align="right">收件人</th>
												<td>${order.receiver }
												</td>
											</tr>
											<tr>
												<th align="right">收件地址</th>
												<td>${order.receiverAddress }
												</td>
											</tr>
											<tr>
												<th align="right">收件人手机</th>
												<td>${order.receiverPhone }
												</td>
											</tr>
											<c:if test="${order.logisticsNo != null }">
												<tr>
													<th align="right">物流单号</th>
													<td>${order.logisticsNo }
													</td>
												</tr>
												<tr>
													<th align="right">物流公司</th>
													<td>${order.logisticsType }
													</td>
												</tr>
												<tr>
													<th align="right">物流费用</th>
													<td>${order.logisticsMoney }
													</td>
												</tr>
												<tr>
													<th align="right">发货时间</th>
													<td>${order.sendTime }
													</td>
												</tr>
												<tr>
													<th align="right">收货时间</th>
													<td>${order.receiveTime }
													</td>
												</tr>
											</c:if>
											<tr>
												<th align="right">备注</th>
												<td>${order.desc }
												</td>
											</tr>
										</table>
										
										<h4 class="lighter">订单商品</h4>
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>商品图片</th>
												<th>商品名</th>
												<th>商品数量</th>
												<th>商品单价</th>
												<th>备注</th>
											</tr>
										</thead>
										<tbody>
										<c:choose>
											<c:when test="${not empty orderDetails}">
												<c:forEach items="${orderDetails}" var="item" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td><img src="${item.productImg }"></td>
														<td>${item.productName }</td>
														<td>${item.num }</td>
														<td>${item.price }</td>
														<td>
															<c:choose>
																<c:when test="${item.money != null }">
																	退款金额：${item.money }；退款状态：
																	<c:if test="${item.status == 1 }">退款申请中</c:if>
																	<c:if test="${item.status == 2 }">退款退货申请中</c:if>
																	<c:if test="${item.status == 3 }">货物已寄出</c:if>
																	<c:if test="${item.status == 4 }">已退款</c:if>
																	；退款原因：${item.reason }；退款时间：${item.createTime }
																</c:when>
																<c:otherwise>
																	无
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center">该订单没有商品详情！</td>
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

