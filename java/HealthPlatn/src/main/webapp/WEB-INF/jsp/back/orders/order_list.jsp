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
	</head>
<body>
		
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="back/showListOrders" method="post" name="orderForm" id="orderForm">
			<table border='0'>
				<tr>
				
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="orderNo" type="text" name="orderNo" value="${pd.orderNo }" placeholder="订单号" />
						</span>
					</td>
					<td style="vertical-align:top;"> 
					 	<span class="input-icon">
							<input autocomplete="off" id="memberNo" type="text" name="memberNo" value="${pd.memberNo }" placeholder="会员号" />
						</span>
					</td>
					<td style="vertical-align:top;"> 
						<c:choose>
							<c:when test="${menuType == 13 }">
								<select class="chzn-select" name="ostatus" id="ostatus" data-placeholder="订单状态" style="vertical-align:top;width: 120px;">
									<option value="" >所有</option> 
									<option value="2" >待发货</option>
									<option value="3" >待退款</option>
							  	</select>
							</c:when>
							<c:when test="${menuType == 14 }">
								<select class="chzn-select" name="ostatus" id="ostatus" data-placeholder="订单状态" style="vertical-align:top;width: 120px;">
									<option value="" >所有</option> 
									<option value="1" >待付款</option>
									<option value="2" >待发货</option>
									<option value="3" >待收货</option>
									<option value="4" >交易完成</option>
									<option value="5" >交易关闭</option>
							  	</select>
							</c:when>
						</c:choose>
					</td>
					<td><input class="span10 date-picker" name="fromTime" id="fromTime" value="${pd.fromTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:135px;" placeholder="订单创建日期起始"/></td>
					<td><input class="span10 date-picker" name="toTime" id="toTime" value="${pd.toTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:135px;" placeholder="订单创建日期结束"/></td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>订单号</th>
						<th>订单名</th>
						<th>会员号</th>
						<th>会员名</th>
						<th>状态</th>
						<th>时间</th>
						<th>市场价</th>
						<th>实付价</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty orderlist}">
						<c:if test="${QX.chas == 1 }">
						<c:forEach items="${orderlist}" var="item" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${item.id }" id="${item.id }"/><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${item.orderNo }</td>
								<td>${item.orderName }</td>
								<td>${item.memberNo }</td>
								<td>${item.memberName }</td>
								<td>
									<c:if test="${item.status == 1 }"><span class="label label-success arrowed">待付款</span></c:if>
									<c:if test="${item.status == 2 }"><span class="label label-important arrowed-in">待发货</span></c:if>
									<c:if test="${item.status == 3 }"><span class="label label-success arrowed">待收货</span></c:if>
									<c:if test="${item.status == 4 }"><span class="label label-success arrowed">交易完成</span></c:if>
									<c:if test="${item.status == 5 }"><span class="label label-success arrowed">交易关闭</span></c:if>
									<c:if test="${item.hasRefund == 1 }"><span >(退款申请中)</span></c:if>
								</td>
								<td>${item.payTime == null ? item.createTime : item.payTime  }</td>
								<td>${item.money}</td>
								<td>${item.payMoney}</td>
								<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
									
										<c:if test="${QX.edits != 1 && QX.dels != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<c:if test="${QX.FW_QX == 1 }">
												<li><a style="cursor:pointer;"  title="查看" onclick="viewOrder('${item.id }');" class="tooltip-warning" data-rel="tooltip" title="" data-placement="left"><span class="blue">查看详情</span></a></li>
												<c:if test="${item.logisticsNo != null }">
													<li><a style="cursor:pointer;"  title="物流" onclick="viewLogistics('${item.logisticsNo }');" class="tooltip-warning" data-rel="tooltip" title="" data-placement="left"><span class="blue">查看物流</span></a></li>
												</c:if>
											</c:if>
											<c:if test="${QX.edits == 1 }">
												<!-- 待发货 或 待退款 -->
												<c:if test="${(item.status == 2 && item.hasRefund == 2) || item.hasRefund == 1}">
													<li><a style="cursor:pointer;" title="处理" onclick="editOrder('${item.id }');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green">处理</span></a></li>
												</c:if>
											</c:if>
											
											<c:choose>
											<c:when test="${user.USERNAME=='admin'}"></c:when>
											</c:choose>
										</ul>
										
										</div>
										
									</div>
								</td>
							</tr>
						
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.chas == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<c:choose>
								<c:when test="${menuType == 13 }">
									<td colspan="100" class="center" >没有待处理数据</td>
								</c:when>
								<c:otherwise>
									<td colspan="100" class="center" >没有相关数据</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
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
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#orderForm").submit();
		}
		
		//查看物流详情
		function viewLogistics(logisticsNo){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="物流跟踪";
			 diag.URL = "<%=basePath%>back/showLogistics.do?logisticsNo="+logisticsNo;
			 diag.Width = 550;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//跳转到资讯查看页面
		function viewOrder(id){
			 top.jzts();
			 window.location.href = "<%=basePath%>back/toOrderView.do?id="+id+"&tm="+new Date().getTime();
		}
		
		//修改
		function editOrder(id){
			 top.jzts();
			 window.location.href = "<%=basePath%>back/toOrderEdit.do?id="+id+"+&tm="+new Date().getTime();
		}
		
		//删除
		function delOrder(id){
			bootbox.confirm("确定要删除这条订单吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>back/deleteOrder.do?id="+id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
						  	else emstr += ';' + document.getElementsByName('ids')[i].id;
						  	
						  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
						  	else phones += ';' + document.getElementsByName('ids')[i].alt;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>back/deleteAllOrder.do?tm='+new Date().getTime(),
						    	data: {News_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage('${page.currentPage}');
									 });
								}
							});
						}
						
					}
				}
			});
		}
		
		</script>
		
	</body>
</html>

