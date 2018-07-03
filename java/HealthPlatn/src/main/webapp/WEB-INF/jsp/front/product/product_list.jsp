<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<c:choose>
	<c:when test="${productList != null && fn:length(productList) != 0 }">
		<div class="row shop-items">
			<c:forEach items="${productList }" var="item">
				<div class="col-lg-4 col-md-4 col-sm-6 " data-dateorder="1" data-popularorder="2" data-avgratingorder="2" data-priceorder="2">
					<!-- Shop Item -->
					<div class="shop-item animate-onscroll">
						<div class="shop-image">
								<div class="shop-featured-image">
									<a onclick="getDetail('${item.id}')">
											<img src="${item.imgPath }" alt="" width="270" height="190">
									</a>
								</div>
						</div>
						<div class="shop-content">
							<h4 style="font-size: 16px"><a onclick="getDetail('${item.id}')">${item.name }</a></h4>
							<span class="price">${item.price }</span>
							
							<a onclick="addToCart('${item.id}')" class="button add-to-cart-button transparent">&nbsp;&nbsp;加入购物车</a>
							<a onclick="getDetail('${item.id}')"  class="button details-button button-arrow transparent">产品详情</a>
						</div>
						
					</div>
					<!-- /Shop Item -->
				
				</div>	
			</c:forEach>
			
		</div>
		<!-- 分页 -->
		<div>
			<div class="divider"></div>
			<div style="float: right;padding-top: 0px;margin-top: 0px;">${page.frontPageStr}</div>
		</div>
	</c:when>
	<c:otherwise>
		<div><p align="center"><br><br><br>没有相关数据</p></div>
	</c:otherwise>
</c:choose>