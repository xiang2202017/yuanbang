<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
	//支付方法
	function pay(){
		var payType = $("input[name='payRadio']:checked").val();
		var total = $("#moneyTotal").html();
		//var address = $("#defaultAddressLabel").html().replace(/&nbsp;&nbsp;&nbsp;&nbsp;/g,"#");
		var address = $("#defaultAddressId").val();
		if(address == ""){
			alert("请先前往会员中心添加收货地址");
			return;
		}
		var tradeno = '';
		var tradename = "";
		if(payType == "zhifubao"){
			var payurlstr = "<%=basePath%>web/member/toPayFrame?totalMoney="+total+"&address="+address;
			
			
			
			 
// 			var diag = new Dialog();
// 			 diag.Drag=true;
// 			 diag.Title ="支付宝付款";
// 			 diag.URL = urlstr;
// 			 diag.Width = 350;
// 			 diag.Height = 450;
// 			 diag.CancelEvent = function(){ //关闭事件
// 				diag.close();
// 			 };
// 			 diag.show();

			$.ajax({
	            url:payurlstr,
	            dataType:"html",
	            //data:{payurlstr:payurlstr},
	            type:"post",
	            success:function(data){
	            	alert(333);
// 	            	 $("#mfDiv").html(data);
	            },
	            error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
	        });

		}else if(payType == "weixin"){
		
		}else if(payType == "yinlian"){
		
		}
	}
	
	function showPayWin(orderno, ordername){
		var total = $("#moneyTotal").html();
		var url = 'shopping_pay.jsp?orderno='+orderno + '&ordername='+ordername + '&money=' + total;
		alert(url);
		//付款窗口
		 var diag = new Dialog();
		 diag.Drag=true;
		 diag.Title ="支付宝付款";
		 diag.URL = url;
// 		 diag.Width = 400;
// 		 diag.Height = 450;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 };
		 diag.show();
	}
	
	//换个邮寄地址
	function changeAddress(){
		$("#toPayContent").hide();
		$('#addressChooseDiv').show();
	}
	
	//选择地址
	function chooseAddress(){
		var addressValue = $("input[name='addressRadio']:checked").val();
		var arr = addressValue.split("#");
		var id = arr[0];
		var name = arr[1];
		var phone = arr[2];
		var address = arr[3];
		$("#defaultAddressLabel").html(name + "&nbsp;&nbsp;&nbsp;&nbsp;" + phone + "&nbsp;&nbsp;&nbsp;&nbsp;" + address);
		$("#defaultAddressId").val(id);
		cancelChooseAddress();
	}
	
	function cancelChooseAddress(){
		$("#toPayContent").show();
		$("#addressChooseDiv").hide();
	}
</script>

<!-- Section -->
<section id="toPayContent" class="section full-width-bg gray-bg" style="padding-top: 0px">
	
	<div class="row" style="padding-top:0px">
	
		<div class="col-lg-12 col-md-12 col-sm-8" style="padding-top: 0px">
		<h3 class="animate-onscroll">您的收货地址</h3>
		<input type="hidden" id="defaultAddressId" value="${defaultAddress == null ? '' :  defaultAddress.id}">
		<c:choose>
			<c:when test="${defaultAddress != null }">
				<label id="defaultAddressLabel">${defaultAddress.name }&nbsp;&nbsp;&nbsp;&nbsp;${defaultAddress.phone }&nbsp;&nbsp;&nbsp;&nbsp;${defaultAddress.address }</label>
				<a style="cursor: pointer;" onclick="changeAddress()">&nbsp;&nbsp;&nbsp;&nbsp;换个地址&nbsp;&gt;&gt;</a>
			</c:when>
			<c:otherwise>
				<label>您还没有收货地址，请先前往会员中心添加！</label>
			</c:otherwise>
		</c:choose>
		
		<h3 class="animate-onscroll">您的订单</h3>
				
				<table class="your-order-table animate-onscroll">
				
					<tr>
						<th>产品</th>
						<th>数量</th>
						<th>单价（元）</th>
					</tr>
					<c:forEach items="${itemList }" var="item">
						<tr>
							<td class="order-product">
								<div class="product-thumbnail">
									<img src="${item.productImg }" alt="">
								</div>
								<p>${item.productName }</p>
							</td>
							<td>
								<p>${item.num }</p>
							</td>
							<th class="price">${item.price }</th>
						</tr>
					</c:forEach>
					
					
					<tr>
						<th colspan="2" class="align-right">订单合计</th>
						<th class="price"><label id="moneyTotal">${total }</label></th>
					</tr>
					
				</table>
			</div>
	</div>
	<div class="row no-margin-bottom">
		<div class="col-lg-12 col-md-12 col-sm-12">
						
						<div class="owl-carousel-container" >
							<div class="owl-header">
								<h3 class="animate-onscroll">支付方式</h3>
							</div>
							<div>
								<div class="col-lg-4 col-md-4 col-sm-4" >
									<div class="blog-post">
										<div>
											<img src="<%=basePath %>static/front_UI/img/zhifubao.jpg" alt="" width="250" height="150">
										</div>
									</div>
									<input checked="checked" type="radio" id="pay_zhifubao" value="zhifubao" name="payRadio" ><label for="pay_zhifubao"><a>支付宝</a></label>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-4" >
									<div class="blog-post animate-onscroll">
										<div>
											<img src="<%=basePath %>static/front_UI/img/weixin.jpg" alt="" width="250" height="150">
										</div>
									</div>
									<input type="radio" id="pay_weixin" value="weixin" name="payRadio" ><label for="pay_weixin"><a>微信</a></label>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-4">
									<div class="blog-post animate-onscroll">
										<div>
											<img src="<%=basePath %>static/front_UI/img/yinlian.jpg" alt="" width="250" height="150">
										</div>
									</div> 
									<input type="radio" id="pay_yinlian" value="yinlian" name="payRadio" ><label for="pay_yinlian"><a>银联</a></label>
								</div>
							</div>
						
						</div>
					</div>
	</div>
	<br><br>
	<p style="text-align: center;"><a class="button" onclick="pay()">去付款</a></p>
<!-- 	<div id="payShow" style="width:500px; height:500px;border: 1px solid red;"></div> -->
</section>

<div id="addressChooseDiv" style="display: none;padding: 50px">
	<div>
		<h4>可选地址列表</h4>
	</div>
	<br><br>
	<c:forEach items="${addressList }" var="item">
			
	    <c:choose>
	    	<c:when test="${defaultAddress != null && defaultAddress.id eq item.id}">
	    		<input checked="checked" type="radio" id="address${item.id }" value="${item.id}#${item.name}#${item.phone}#${item.address}" name="addressRadio" ><label for="address${item.id }"><a>${item.name } &nbsp;&nbsp;&nbsp;&nbsp;${item.phone }&nbsp;&nbsp;&nbsp;&nbsp;${item.address }</a></label>
	    	</c:when>
	    	<c:otherwise>
				<input type="radio" id="address${item.id }"  value="${item.id}#${item.name}#${item.phone}#${item.address}" name="addressRadio" ><label for="address${item.id }"><a>${item.name } &nbsp;&nbsp;&nbsp;&nbsp;${item.phone }&nbsp;&nbsp;&nbsp;&nbsp;${item.address }</a></label>
	    	</c:otherwise>
	    </c:choose>
		<br>
		<hr>
		<br>
	</c:forEach>
	<div style="padding-bottom: 50px;text-align: center">
		<a class="button" onclick="chooseAddress()">确定</a>
		<a class="button" onclick="cancelChooseAddress()">取消</a>
	</div>
</div>


		<!--引入弹窗组件start-->
		<script type="text/javascript" src="<%=basePath%>plugins/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="<%=basePath%>plugins/attention/zDialog/zDialog.js"></script>