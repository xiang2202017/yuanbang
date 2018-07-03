<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>



<script type="text/javascript">
		
	//提交退款申请
	function refundLogistics(refundId){
		if($("#logisticsNo").val().length == 0){
			alert("请输入退货的物流号！");
			return;
		}
		var logisticsNo = $("#logisticsNo").val();
		$.ajax({
            url:"<%=basePath%>web/member/refundUpdate.do",
            data:{"refundId":refundId,"logisticsNo":logisticsNo},
            dataType:"html",
            type:"post",
            success:function(data){
            	var value = JSON.parse(data);
            	if(value.result == "success"){
	                alert("物流信息更新成功");
	                //div加载页面
                	toOrder();
            	}else{
            		alert("物流信息更新失败");
            	}
            }
        });
	}
	
	function refundRetry(refundId){
		var reason = $("#reason").val();
		var money = $("#money").val();
		if(money.length == 0){
			alert("请输入退货金额！");
			return;
		}
		if(reason.length == 0){
			alert("请填写退款原因！");
			return;
		}
		$.ajax({
            url:"<%=basePath%>web/member/refundUpdate.do",
            data:{"reason":reason,"money":money,"refundId":refundId},
            dataType:"html",
            type:"post",
            success:function(data){
                alert("退款申请成功");
                //div加载页面
                showList(typeId);
            }
        });
	}
	
	//取消退款申请
	function refundWithdraw(refundId){
		$.ajax({
            url:"<%=basePath%>web/member/refundDel.do",
            data:{"refundId":refundId},
            dataType:"html",
            type:"post",
            success:function(data){
                alert("退款申请已撤销");
                //div加载页面
                showList(typeId);
            }
        });
	}
	
	function refundBack(){
		$("#refundLogisticsBtn").attr("disabled", true);
		lastMemberPage.pop();
		$("#listDiv").html(lastMemberPage[lastMemberPage.length - 1]);
	}
</script>

<div id="phonediv">
	<h4>退换处理</h4>
	<table>
		<tr>
			<td>订单编号：</td>
			<td><label>${order.orderNo }</label></td>
		</tr>
		<tr>
			<td><img src="${orderDetail.productImg }"></td>
			<td>${orderDetail.productName }</td>
		</tr>
		<tr >
			<td colspan="2">
				<div style="text-align: left;">
					<input type="hidden" value="${order.id }" id="orderId"/>
					退款进度：<font color="orange">
					<c:if test="${refund.status == 1 }">
						<label>退款申请中</label>
					</c:if>
					<c:if test="${refund.status == 2 }">
						<label>退款退货申请中</label>
					</c:if>
					<c:if test="${refund.status == 3 }">
						<label>货物已寄出</label>
					</c:if>
					<c:if test="${refund.status == 4 }">
						<label>已退款</label>
					</c:if></font>
				</div>
			</td>
		</tr>
		<c:if test="${refund.result == 1 }">
			<tr>
				<td>申请结果：</td>
				<td>
					通过
				</td>
			</tr>				
			<tr>
				<td>退款金额：</td>
				<td><label>${refund.money }</label></td>
			</tr>
			<tr>
				<td>退款原因：</td>
				<td><label>${refund.reason }</label></td>
			</tr>
			<tr>
				<td>申请时间：</td>
				<td><label>${refund.createTime }</label></td>
			</tr>
			<c:if test="${refund.status == 2 }">
				<tr id="changeTR">
					<td>请填写退货物流单号：</td>
					<td><input type="text" id="logisticsNo" name="logisticsNo"></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<p align="center">
							<a class="button" onclick="refundLogistics('${refund.id}')">提交物流</a> 
							<a class="button" onclick="refundWithdraw('${refund.id}')">撤销申请</a> 
							<a class="button" id="refundLogisticsBtn" onclick="refundBack()">返回</a> 
						</p>
					</td>
				</tr>
			</c:if>
			<c:if test="${(refund.status == 3 || refund.status == 4) }">
				<c:if test="${refund.logisticsNo != null }">
					<tr>
						<td>退货物流单号：</td>
						<td><label>${refund.logisticsNo }</label></td>
					</tr>
				</c:if>
				<tr align="center">
					<td colspan="2">
						<p align="center">
							<a class="button" id="refundLogisticsBtn" onclick="refundBack()">返回</a> 
						</p>
					</td>
				</tr>
			</c:if>
			
		</c:if>
		
		<c:if test="${refund.result == 2 }">
			<tr>
				<td>申请结果：</td>
				<td>
					未通过
				</td>
			</tr>
			<tr>
				<td>退款金额：</td>
				<td><input type="text" id="money" name="money" value="${order.money }"></td>
			</tr>
			<tr>
				<td>退款原因：</td>
				<td><input type="text" id="reason" name="reason"></td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<p align="center">
						<a class="button" onclick="refundRetry('${refund.id}')">重新提交申请</a> 
						<a class="button" onclick="refundWithdraw('${refund.id}')">撤销申请</a> 
						<a class="button" onclick="refundBack()">返回</a> 
					</p>
				</td>
		</tr>
		</c:if>
	</table>
</div>
