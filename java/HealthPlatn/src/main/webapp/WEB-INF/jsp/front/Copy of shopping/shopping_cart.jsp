<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
	var prenum;
	
	//数量编辑
	function toEdit(id){
		$("#showUl"+id).hide();
		$("#editUl"+id).show();
		prenum = $("#pnumLab"+id).html();
		$("#pnumInp"+id).val(prenum);
	}
	
	//保存修改数量
	function saveNum(productId,price,id){
		var pnum = $("#pnumInp"+id).val();
		if(pnum == ""){
			alert("请输入商品数量");
			return;
		}
		$("#showUl"+id).show();
		$("#editUl"+id).hide();
		
		$.ajax({
            url:"<%=basePath%>web/member/updateCartItem.do",
            dataType:"json",
            data:{"productId":productId, num:pnum},
            type:"post",
            success:function(data){
            	if(data.result == "fail"){
            		alert("商品数量修改失败");
            	}else{
            		//更新总金额
					var money = ((pnum - prenum) * price).toFixed(2);
				    var total = $("#moneyTotal").html();
				    $("#pnumLab"+id).html(pnum);
				    $("#moneyTotal").html((parseFloat(total) + parseFloat(money)).toFixed(2));
            	}
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) { 
            	alert(XMLHttpRequest.status); 
            	alert(XMLHttpRequest.readyState); 
            	alert(textStatus); 
            }
        });
	}
	
	function delProduct(id,price,num){
		var msg = "您真的确定要删除吗？\n\n请确认！"; 
		if (confirm(msg)!=true){ 
			return; 
		}
		var urlstr = "<%=basePath%>web/member/delCartItem";
		$.ajax({
            url:urlstr,
            data:{"id":id},
            dataType:"json",
            type:"post",
            success:function(data){
            	if(data.result == "fail"){
            		alert("商品删除失败");
            	}else{//删除成功
            		//删除行
            		 $("#tr"+id).remove();  
            		//更新总金额
					var money = price * num;
				    var total = $("#moneyTotal").html();
				    $("#moneyTotal").html((total - money).toFixed(2));
				           	
            	}
            }
        });
	}
	
	//获取产品详情
	function getDetail(productId){
		var urlstr = "<%=basePath%>web/toProductView.do";
		$.ajax({
            url:urlstr,
            data:{"productId":productId},
            dataType:"html",
            type:"post",
            success:function(data){
            	//alert(data);
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
	
	//跳转到支付页面
	function toPay(){
		var urlstr = "<%=basePath%>web/member/toPayPage.do";
		var moneyTotal = $("#moneyTotal").html();
		if(moneyTotal == 0){
			alert("您的购物车为空，请先选择商品");
			return;
		}
		$.ajax({
            url:urlstr,
            data:{"total":moneyTotal},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
</script>

<!-- Section -->
<section class="section full-width-bg gray-bg">
	
	<div class="row">
	
		<div class="col-lg-12 col-md-12 col-sm-8">
			<div style=" position: relative; padding: 0;margin: 0">
				<div style="float:left;padding-bottom: 5px;"><font size="4px" style="font-weight: bold;">已选商品列表</font></div>
			</div>
			<table class="shopping-cart-table">
				
				<tr>
<!-- 					<td style="width: 30px;vertical-align: middle;"> -->
<!-- 						<input type="checkbox" id="zcheckbox" name="zcheckbox"/><label for="zcheckbox"></label> -->
<!-- 					</td> -->
					<th>商品</th>
					<th>单价</th>
					<th>数量</th>
					<th></th>
				</tr>
				<c:forEach items="${productlist }" var="item">
					<tr id="tr${item.id }">
<!-- 						<td class='center' style="width: 30px;"> -->
<!-- 							<input type='checkbox' name='ids' id="${item.id }"/><label for="${item.id }"></label> -->
<!-- 						</td> -->
						<td class="shopping-cart-item">
							<div class="product-thumbnail">
								<img src="${item.productImg }" alt="">
							</div>
							<h6><a href="#">${item.productName }</a></h6>
						</td>
						<td class="price">${item.price }</td>
						<td>
							<div style="width: 100px">
								<ul id="showUl${item.id }" class="list-inline">
									<li><label id="pnumLab${item.id }">${item.num }</label></li>
									<li><a class="btn icon-pencil" href="#" onclick="toEdit('${item.id}')"></a></li>
								</ul>
								<ul id="editUl${item.id }" class="list-inline" style="display: none;">
									<li><input id="pnumInp${item.id }" type="text" value="${item.num }" style="width: 50px"></li>
									<li><a class="btn icon-ok" href="#" onclick="saveNum('${item.productId}','${ item.price}','${item.id }')"></a> </li>
								</ul>
							</div>
						</td>
						<td><i onclick="delProduct('${item.id}','${item.price }','${item.num }')" class="icons remove-shopping-item icon-cancel-circled"></i></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4" class="align-right">
						总价：<label id="moneyTotal">${total}</label> &nbsp;&nbsp;&nbsp;<a class="button button-arrow donate" onclick="toPay()">结算</a>
					</td>
				</tr>
			</table>
			
			<!-- Related Products -->
						<div class="row related-products">
							
							<div class="col-lg-12 col-md-12 col-sm-12 animate-onscroll">
								<h3>您可能感兴趣...</h3>
							</div>
							
							<c:forEach items="${relatedList }" var="item">
								<div class="col-lg-4 col-md-4 col-sm-6">
								
									<!-- Shop Item -->
									<div class="shop-item animate-onscroll">
										
										<div class="shop-image">
											<a  onclick="getDetail('${item.id}')">
												<div class="shop-featured-image">
													<img src="${item.imgPath }" alt="" width="360" height="230">
												</div>
											</a>
										</div>
										
										<div class="shop-content">
											
											<h4><a  onclick="getDetail('${item.id}')">${item.name }</a></h4>
											<span class="price">${item.price}</span>
											
											<div class="shop-rating read-only" data-score="0"></div>
											
											<a onclick="getDetail('${item.id}')" class="button details-button button-arrow transparent">查看详情</a>
											
										</div>
										
									</div>
									<!-- /Shop Item -->
								
								</div>
							</c:forEach>
						
						</div>
						<!-- /Related Products -->
		</div>
		
	</div>
</section>