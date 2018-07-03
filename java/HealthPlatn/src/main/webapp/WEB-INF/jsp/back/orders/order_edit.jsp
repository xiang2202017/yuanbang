<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../top.jsp"%> 
	
	
	<style type="text/css">
	#uploadDiv{float: left;width: 100%;}
	#waitDiv{position: absolute;z-index: 999999;width:100%; height:100%;}
	</style>
	
	<script type="text/javascript">
		
		
		
	
	</script>
	
	</head> 
<body>
	
<div id="waitDiv" style="display: none;background-color: gray;">
	<img src="<%=basePath%>static/img/wait.gif"/>
</div>

<div class="container-fluid" id="main-container">



<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


 	<div class="span12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large" style="min-height:38px;height: 38px; line-height: 38px;padding-right: 8px">
				<h4 class="lighter">订单更新</h4>
				<a class="lighter" style="cursor: pointer; padding-right: 8px;"
								onclick="returntoList()">返回</a>
			</div>
			<div class="widget-body">
			 
			 
			 <div class="widget-main">
			 <div class="step-content row-fluid position-relative">
				<form action="back/updateOrder" name="orderForm" id="orderForm" method="post">
					<div id="zhongxin">
						<table style="width:100%;" id="xtable">
							<tr>
								<td align="right">订单号</td>
								<td>
									<input type="hidden" id="id" name="id" value="${order.id }">
									<input type="text" name="orderNo" id="orderNo" disabled="disabled" value="${order.orderNo}" />
								</td>
							</tr>
							<tr>
								<td align="right">订单状态</td>
								<td>
									<c:if test="${order.hasRefund == 1 }">
										<span class="label "> 待退款</span>
									</c:if> 
									<c:if test="${order.hasRefund == 2 }">
										<c:choose>
											<c:when test="${order.status == 2 }">
												<span class="label ">待发货</span>
											</c:when>
											<c:otherwise>
												<c:if test="${order.status == 1 }"><span class="label">待付款</span></c:if>
												<c:if test="${order.status == 3 }"><span class="label">待收货</span></c:if>
												<c:if test="${order.status == 4 }"><span class="label">交易完成</span></c:if>
												<c:if test="${order.status == 5 }"><span class="label">交易关闭</span></c:if>
											</c:otherwise>
										</c:choose>
										
									</c:if>
								</td>
							</tr>
							<tr>
								<td align="right">会员号</td>
								<td>
									<input type="text" name="memberNo" id="memberNo" disabled="disabled" value="${order.memberNo}" />
								</td>
							</tr>
							<tr>
								<td align="right">总金额</td>
								<td>
									<c:choose>
										<c:when test="${order.status == 1 }">
											<input type="text" name="money" id="money" value="${order.money}" />
										</c:when>
										<c:otherwise>
											<input type="text" name="money" id="money" disabled="disabled" value="${order.money}" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td align="right">支付方式</td>
								<td>
									<c:if test="${order.payType == 1 }">
										<span class="label "> 支付宝</span>
									</c:if> 
									<c:if test="${order.payType == 2 }">
										<span class="label "> 微信</span>
									</c:if> 
									<c:if test="${order.payType == 3 }">
										<span class="label "> 银联</span>
									</c:if> 
								</td>
							</tr>
							<tr>
								<td align="right">支付时间</td>
								<td>
									<input type="text" name="payTime" id="payTime" disabled="disabled" value="${order.payTime}" />
								</td>
							</tr>
							<tr>
								<td align="right">支付号</td>
								<td>
									<input type="text" name="payNo" id="payNo" disabled="disabled" value="${order.payNo}" />
								</td>
							</tr>
							<!-- 发货 -->
							<c:if test="${ order.hasRefund == 2 && order.status == 2}">
								<tr>
									<td align="right">发货的物流单号</td>
									<td>
										<input type="text" name="logisticsNo" id="logisticsNo"  />
									</td>
								</tr>
								<tr>
									<td align="right">物流公司</td>
									<td>
										<input type="text" name="logisticsType" id="logisticsType"  placeholder="输入如顺丰"/>
									</td>
								</tr>
								<tr>
									<td align="right">快递费用</td>
									<td>
										<input type="text" name="logisticsMoney" id="logisticsMoney" value="10" />
									</td>
								</tr>
								<tr>
									<td align="center" colspan="2">
										<button type="button" onclick="formSubmit()">提交</button>
										<button type="button" onclick="cancel()">取消</button>
									</td>
								</tr>
							</c:if>
						</table>
						
						<!-- 退款 -->
						<c:if test="${ order.hasRefund == 1 }">
							<h4 class="lighter">订单商品</h4>
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>商品名</th>
												<th>商品图片</th>
												<th>商品数量</th>
												<th>商品单价</th>
												<th>申请退款金额</th>
												<th>申请状态</th>
												<th>退款原因</th>
												<th>申请时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
										<c:choose>
											<c:when test="${not empty productlist}">
												<c:forEach items="${productlist}" var="item" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td>${item.productName }</td>
														<td><img src="${item.productImg }"></td>
														<td>${item.num }</td>
														<td>${item.price }</td>
														<td>${item.money }</td>
														<td>
															<c:if test="${item.status == 1 }">退款申请中</c:if>
															<c:if test="${item.status == 2 }">退款退货申请中</c:if>
															<c:if test="${item.status == 3 }">货物已寄出</c:if>
															<c:if test="${item.status == 4 }">已退款</c:if>
														</td>
														<td>${item.reason }</td>
														<td>${item.createTime }</td>
<!-- 														<td style="width: 30px;" class="center"> -->
<!-- 															<div class='hidden-phone visible-desktop btn-group'> -->
															
<!-- 																<c:if test="${QX.edits != 1}"> -->
<!-- 																<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span> -->
<!-- 																</c:if> -->
<!-- 																<c:if test="${item.refundStatus == 1 || item.refundStatus == 2 }"> -->
<!-- 																	<button onclick="dealOrder('${item.id }','${item.refundId }');">处理</button> -->
<!-- 																</c:if> -->
<!-- 															</div> -->
<!-- 														</td> -->
														<td>
															<c:choose>
																<c:when test="${((item.status == 1 || item.status == 2) && (item.result == null || item.result == 2 ))|| item.status == 3}">
																	<a onclick="dealOrder('${item.refundId}')" style="cursor: pointer;">处理</a>
																</c:when>
																<c:otherwise>
																	<c:if test="${item.result == 1 }">处理意见：同意</c:if>
																	<c:if test="${item.result == 2 }">处理意见：拒绝</c:if>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center">没有相关退款申请记录!</td>
												</tr>
											</c:otherwise>
										</c:choose>
										</tbody>
									</table>
						</c:if>
						<!-- 发货 -->
						<c:if test="${ order.hasRefund == 2}">
						<br><br><br>
							<hr width="100%" align="center">
							<br>
							<h4 class="lighter">订单商品</h4>
							<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>商品名</th>
												<th>商品图片</th>
												<th>商品数量</th>
												<th>商品单价</th>
												<th>备注</th>
											</tr>
										</thead>
										<tbody>
										<c:choose>
											<c:when test="${not empty productlist}">
												<c:forEach items="${productlist}" var="item" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td>${item.productName }</td>
														<td><img src="${item.productImg }"></td>
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
										</c:choose>
										</tbody>
									</table>
						</c:if>
					</div>
				</form>


			 </div> 
			 </div><!--/widget-main-->
			</div><!--/widget-body-->
		</div>
	</div>
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.7.2.js'>\x3C/script>");</script>
		<script type="text/javascript" src="static/js/ajaxfileupload.js"></script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript" src="static/js/bootbox.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/toolEmail.js"></script>	
		<script type="text/javascript" src="static/js/myjs/image.js"></script>	
		
		<script type="text/javascript">
			//拒绝退货
			function refuseOrder(detailId){
				$("#orderForm").attr('action','<%=basePath%>back/refuseRefund.do?detailId='+detailId);
				$("#orderForm").submit();
			}
			
			//同意退货
			function dealOrder(refundId){
				top.jzts();
			 	window.location.href = '<%=basePath%>back/dealRefund.do?refundId='+refundId + '&tm='+new Date().getTime();
			}
		
			//更新物流单号
			function formSubmit(){
				var no = $("#logisticsNo").val();
				if(no == ""){
					alert("请填写物流单号");
					return;
				}
				$("#orderForm").submit();
			}
			
			function cancel(){
				history.back();
			}
			
			function returntoList() {
				history.back();
			}
		</script>
		
	</body>
</html>

