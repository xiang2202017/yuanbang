<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<h4 style="text-align: center;">在所选时间范围内，会员实时业绩查询结果</h4>

<label>总销售金额为：</label><font size="15" color="orange"><label id="totalMoney">请选择搜索时间！</label></font>&nbsp;&nbsp;&nbsp;&nbsp;累计销售积分：<font size="15" color="orange"><label id="totalScore">请选择搜索时间！</label></font>

<c:choose>
		<c:when test="${orderlist == null || fn:length(orderlist) == 0 }">
			<div style="text-align: center;">暂时没有相关数据!</div>
		</c:when>
		<c:otherwise>
			<c:forEach items="${orderlist }" var="order">
				<table>
					<tr>
						<td colspan="4">
							订单编号：${order.orderNo }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单创建时间：${order.createTime }
						</td>
					</tr>
					<c:forEach items="${ order.productlist }" var="product">
						<tr>
							<td><img src="${product.productImg }"></td>
							<td>${product.productName }</td>
							<td>数量：${product.num }</td>
							<td>单价：${product.price }</td>
							<td><font size="15" color="orange">积分值：${product.score }</font></td>
						</tr>
					</c:forEach>
				</table>
				<br/>
			</c:forEach>
		</c:otherwise>
	</c:choose>

<!-- 分页 -->
<div >
	<div class="divider"></div>
	<div style="float: right;padding-top: 0px;margin-top: 0px;">${page.frontPageStr}</div>
</div>