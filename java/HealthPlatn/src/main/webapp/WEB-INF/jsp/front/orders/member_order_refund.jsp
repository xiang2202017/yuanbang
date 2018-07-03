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
	function refundApply(orderNo){
		var status = $("#orderStatus").val();
		var type;
		alert(status);
		if(status == 2){
			type = $("input[name='fundR']:checked").val(); 
		}else if(status == 3){
			type = $("#fundS").val();
		}
		var reason = $("#reason").val();
		var money = $("#money").val();
		var orderId = $("#orderId").val();
		var orderDetailId = $("#orderDetailId").val();
		if(reason == ""){
			alert("请填写退款原因");
			return;
		}
		$.ajax({
            url:"<%=basePath%>web/member/refundApply.do",
            data:{"type":type, "reason":reason,"money":money,"orderId":orderId, "orderNo":orderNo, "orderDetailId":orderDetailId},
            dataType:"html",
            type:"post",
            success:function(data){
            	data = JSON.parse(data);
            	if(data.result == "success"){
	                alert("退款申请成功");
            	}else{
            		alert("退款申请失败");
            	}
                //div加载页面
                toOrder();
            }
        });
	}
	
	//取消退款申请
	function refundCancle(){
		needRefresh = -1;
		$("#refundCancelBtn").attr("disabled", true);
		lastMemberPage.pop();
		$("#listDiv").html(lastMemberPage[lastMemberPage.length - 1]);
	}
</script>

<div id="phonediv">
	<h4>退换申请</h4>
	<table>
		<tr >
			<td>退款类别：</td>
			<td>
				<div style="text-align: center;">
					<input type="hidden" value="${order.id }" id="orderId"/>
					<input type ="hidden" value="${orderDetail.id }" id="orderDetailId">
					<input type="hidden" value="${order.status }" id="orderStatus"> 
					<c:if test="${order.status == 2 }">
						<input type="radio" name="fundR" value="1" checked>仅退款
					</c:if>
					<c:if test="${order.status == 3 }">
						<select name="fundS" id="fundS">
							<option value="1" selected="selected">仅退款</option>
							<option value="2" >退货退款</option>   
						</select>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<td>退款金额：</td>
			<td><input type="text" id="money" name="money" value="${orderDetail.price }"></td>
		</tr>
		<tr>
			<td>退款原因：</td>
			<td><input type="text" id="reason" name="reason"></td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<p align="center">
					<a class="button" onclick="refundApply('${order.orderNo}')">提交申请</a> &nbsp;&nbsp;&nbsp;&nbsp;
					<a class="button" id="refundCancelBtn" onclick="refundCancle()">取消</a> 
				</p>
			</td>
		</tr>
	</table>
</div>
