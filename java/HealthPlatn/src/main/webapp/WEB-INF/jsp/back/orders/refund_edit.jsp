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
			<div class="widget-body">
			 
			 
			 <div class="widget-main">
			 <div class="step-content row-fluid position-relative">

				<form action="back/updateRefund" name="refundForm" id="refundForm" method="post">
					<div id="zhongxin">
					<input type="hidden" value="${refund.id }" id="id" name="id"/>
					<table style="width:100%;" id="xtable">
						<tr>
							<td align="right">申请时间：</td>
							<td style="margin-top:0px;">
								<input type="text" value="${refund.createTime }" disabled="disabled">
							</td>
						<tr>
						<tr>
							<td align="right">申请退款金额：</td>
							<td style="margin-top:0px;">
								<input type="text" value="${refund.money }" disabled="disabled">
							</td>
						<tr>
						<tr>
							<td align="right">原因：</td>
							<td style="margin-top:0px;">
								<input type="text" value="${refund.reason }" disabled="disabled">
							</td>
						<tr>
						
						<c:choose>
							<c:when test="${refund.result == null || refund.result == 2 }">
								<!-- 退款申请 -->
								<c:if test="${refund.status == 1 }">
									<tr>
										<td width="20%" align="right">处理结果：</td>
										<td>
											<select name="result" id="result" onchange="resultChoose()">
												<option value="1" selected="selected">同意</option>
												<option value="2" >拒绝</option>   
											</select>
										</td>
									</tr>
									<tr>
										<td align="right">处理意见：</td>
										<td style="margin-top:0px;">
											<textarea rows="5" cols="5" id="dealReason" name="dealReason"></textarea>
										</td>
									</tr>
									<tr class="refundPay">
										<td align="right">退款方式：</td>
										<td>
											<select name="tradeType" id="tradeType">
												<option value="1"  <c:if test="${order.payType == 1}">selected</c:if>>支付宝</option>
												<option value="2"  <c:if test="${order.payType == 2}">selected</c:if>>微信</option>  
												<option value="3"  <c:if test="${order.payType == 3}">selected</c:if>>银联</option>    
											</select>
										</td>
									</tr>
									<tr class="refundPay">
										<td align="right">退款流水号：</td>
										<td><input type="text" name="tradeNo" id="tradeNo"></td>
									</tr>
								</c:if>
								
								<!-- 退款退货申请 -->
								<c:if test="${refund.status == 2 }">
									<tr>
										<td width="20%" align="right">处理结果：</td>
										<td>
											<select name="result" id="result">
												<option value="1" selected="selected">同意</option>
												<option value="2" >拒绝</option>   
											</select>
										</td>
									</tr>
									<tr>
										<td align="right">处理意见：</td>
										<td style="margin-top:0px;">
											<textarea rows="5" cols="5" id="dealReason" name="dealReason"></textarea>
										</td>
									</tr>
								</c:if>
							</c:when>
							<c:otherwise>
								<tr>
									<td width="20%" align="right">处理结果：</td>
									<td>
										<c:if test="${refund.result == 1 }">同意</c:if>
										<c:if test="${refund.result == 2 }">拒绝</c:if>
									</td>
								</tr>
								<tr>
									<td align="right">处理意见：</td>
									<td style="margin-top:0px;">
										<textarea rows="5" cols="5" id="dealReason" name="dealReason" disabled="disabled">${refund.dealReason }</textarea>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						
						
						<!-- 退款退货申请    --  已收到货，退款 -->
						<c:if test="${refund.status == 3 }">
							<tr>
								<td align="right">物流号：</td>
								<td><a>${refund.logisticsNo }</a></td>
							</tr>
							<tr>
								<td align="right">寄出时间：</td>
								<td>${refund.sendTime }</td>
							</tr>
							<tr >
								<td align="right">退款方式：</td>
								<td>
									<select name="tradeType" id="tradeType">
										<option value="1"  <c:if test="${order.payType == 1}">selected</c:if>>支付宝</option>
										<option value="2"  <c:if test="${order.payType == 2}">selected</c:if>>微信</option>  
										<option value="3"  <c:if test="${order.payType == 3}">selected</c:if>>银联</option>    
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">退款流水号：</td>
								<td><input type="text" name="tradeNo" id="tradeNo"></td>
							</tr>
						</c:if>
						<tr>
							<td></td>
							<td align="center">
								<c:choose>
									<c:when test="${refund.status == 2 && refund.result == 1 }">
										<button type="reset" onclick="cancel()">返回</button>
									</c:when>
									<c:otherwise>
										<button type="button" onclick="formSubmit()">保存</button>
										<button type="reset" onclick="cancel()">取消</button>
									</c:otherwise>
								</c:choose>
									
							</td>
						</tr>
					</table>
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
		<script type="text/javascript" src="static/js/myjs/image.js"></script>	
		
		<script type="text/javascript">
			$(top.hangge());
			
			//控制下拉框结果
			function resultChoose(){
				var result = $("#result option:selected").val();
				if(result == 1){
					$(".refundPay").show();
				}else{
					$(".refundPay").hide();
				}
			}
			
			//提交表单
			function formSubmit(){
				if($("#tradeNo").length > 0 && $("#tradeNo").is(":visible")&&$("#tradeNo").val() == ""){
					alert("请填写退款交易号");
					return;
				}
				
				if($("#result").length > 0){
					var result = $("#result option:selected").val();
					if(result == 2){
						var reason = $("#dealReason").val();
						if(reason == ""){
							alert("请填写拒绝的原因");
							return;
						}
					}
				}
				$("#refundForm").submit();
			}
			
			function cancel(){
				history.back();
			}
		</script>
		
	</body>
</html>

