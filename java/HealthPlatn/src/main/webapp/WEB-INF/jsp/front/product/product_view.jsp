<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
//添加到购物车
	function addToCart_forDetail(id){
		var num = $("#numinp").val();
		if(num == ""){
			alert("请填写购买的数量");
			return;
		}
		$.ajax({
            url:"<%=basePath%>web/member/addToCart.do",
            data:{"productId":id, "num":num},
            dataType:"json",
            type:"post",
            success:function(data){
            	var result = data.result;
            	if(result == "fail"){
            		alert("添加失败");
            	}else if(result == "success"){
                	alert("已添加到购物车");
                }else if(result == "nologin"){
                	alert("您还未登录，请先登录后再来操作");
                }
            }
        });
	}
</script>

<!-- 	<section class="section gray-bg"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-lg-9 col-md-9 col-sm-8"> -->
				
				<!-- Product Single -->
				<div class="shop-single">
					<!-- Product Gallery -->
					<div class="shop-product-gallery animate-onscroll">
						<div class="main-image">
							<img class="cloud-zoom-image" src="${pd.imgPath }" alt="" width="480" height="320">
						</div>
<!-- 						<ul class="slider-navigation"> -->
<!-- 							<li> -->
<!-- 								<a href="img/shop/item1-big.jpg" class="jackbox" data-group="shop-product-gallery"> -->
<!-- 									<img src="${pd.imgPath }" alt=""> -->
<!-- 								</a> -->
<!-- 							</li> -->
<!-- 							<li> -->
<!-- 								<a href="img/shop/item1-2-big.jpg" class="jackbox" data-group="shop-product-gallery"> -->
<!-- 									<img src="${pd.imgPath }" alt=""> -->
<!-- 								</a> -->
<!-- 							</li> -->
<!-- 						</ul> -->
					</div>
					<!-- /Product Gallery -->
					
					<!-- Shop Product Content -->
					<div class="shop-product-content">
						<h2 class="animate-onscroll">${pd.name }</h2>
						<div class="shop-rating read-only animate-onscroll" data-score="3.5"><br/></div>
						<span class="price animate-onscroll">${pd.price }（元） &nbsp;&nbsp;&nbsp;&nbsp;</span>
						<p class="animate-onscroll">${pd.keywords }</p>
						<div class="animate-onscroll">
							<input class="numeric-input" id="numinp" type="text" value="1">
						</div>
						<a href="#" onclick="addToCart_forDetail('${pd.id}')" class="add-to-cart-button button donate animate-onscroll">加入购物车</a>
						<p><label>积分值：${pd.points }</label></p>
<!-- 						<ul class="social-share animate-onscroll">	 -->
<!-- 							<li>分享到:</li> -->
<!-- 							<li class="pinterest"><a href="#" class="tooltip-ontop" title="weixin"><i class="icons icon-pinterest-3"></i></a></li> -->
<!-- 						</ul> -->
						<p class="animate-onscroll">分类: <a href="#">${pd.typeName }</a></p>
					</div>
					<!-- /Shop Product Content -->
					
				</div>
				<!-- /Product Single -->
				
				
				<div class="alert-box alert-box-button info animate-onscroll">
<!-- 					<div class="row"> -->
<!-- 						<div class="col-lg-9 col-md-9 col-sm-8"> -->
							<p><a id="discA">产品描述 </a></p>
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
				<!-- 商品描述 -->
				<div class="tab-content">
					<p>${pd.discription }</p>
				</div>
				
				<div class="alert-box alert-box-button info animate-onscroll">
<!-- 					<div class="row"> -->
<!-- 						<div class="col-lg-9 col-md-9 col-sm-8"> -->
							<p><a id="dissA">产品评论</a> </p>
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
				<!-- 评论 -->
				<div class="row">
					<ul>
						<li>暂无评论</li>
					</ul>
<!-- 					<ul > -->
<!-- 						<li> -->
<!-- 							<h5>Gloria Mann</h5> -->
<!-- 							<span class="date">December 12, 2013</span> -->
<!-- 							<p>Ut tellus dolor, dapibus eget, elementum vel, cursus eleifend, elit. Aenean auctor wisi et urna. Aliquam erat volutpat.</p> -->
<!-- 						</li> -->
<!-- 					</ul> -->
				</div>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</section> -->
