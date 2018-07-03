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
			<form action="back/listMemberJoin" method="post" name="memberJoinForm" id="memberJoinForm">
			<table border='0'>
				<tr>
				
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="memberNo" type="text" name="memberNo" value="${pd.memberNo }" placeholder="这里输入会员号" />
						</span>
					</td>
					<td style="vertical-align:top;"> 
					 	<select name="typeId" id="typeId" data-placeholder="请选择加盟状态" style="vertical-align:top;width: 120px;">
					 		<c:choose>
					 			<c:when test="${pd.typeId == '1' }">
					 				<option value="1" selected="selected">待处理</option>
					 				<option value="2">已处理</option>
									<option value="3">已过期</option>
					 			</c:when>
					 			<c:when test="${pd.typeId == '2' }">
					 				<option value="1">待处理</option>
					 				<option value="2" selected="selected">已处理</option>
									<option value="3">已过期</option>
					 			</c:when>
					 			<c:when test="${pd.typeId == '3' }">
					 				<option value="1">待处理</option>
					 				<option value="2">已处理</option>
									<option value="3" selected="selected">已过期</option>
					 			</c:when>
					 		</c:choose>
					  	</select>
					</td>
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
						<th>会员号</th>
						<th>会员名</th>
						<th>会员手机</th>
						<th>身份证</th>
						<th>当前状态</th>
						<th>邮箱</th>
						<th>申请时间</th>
						<th>会员留言</th>
						<th>处理说明</th>
						<th>处理时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty memberJoinList}">
						<c:if test="${QX.chas == 1 }">
						<c:forEach items="${memberJoinList}" var="item" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${item.id }" id="${item.id }"/><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${item.memberNo }</td>
								<td>${item.memberName }</td>
								<td>${item.phone }</td>
								<td>${item.idcardNo }</td>
								<td>
									<c:if test="${item.status == '1' }">申请中</c:if>
									<c:if test="${item.status == '2' }">已加盟</c:if>
									<c:if test="${item.status == '3' }">已过期</c:if>
								</td>
								<td>${item.mail }</td>
								<td>${item.createTime }</td>
								<td>${item.message }</td>
								<td>${item.disposeDesc }</td>
								<td>${item.dealTime }</td>
								<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
									
										<c:if test="${QX.edits != 1 && QX.dels != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<c:if test="${QX.FW_QX == 1 }">
												<li><a style="cursor:pointer;"  title="查看" onclick="viewMemberJoin('${item.id }');" class="tooltip-warning" data-rel="tooltip" title="" data-placement="left"><span class="blue"><i class='icon-search'></i></span></a></li>
											</c:if>
											<c:if test="${QX.edits == 1 }">
												<c:if test="${item.status == '1' }">
													<li><a style="cursor:pointer;" title="处理" onclick="editMemberJoin('${item.id }');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left">处理<span class="green"><i class="icon-edit"></i></span></a></li>
												</c:if>
											</c:if>
											<c:choose>
											<c:when test="${user.USERNAME=='admin'}"></c:when>
											<c:otherwise>
												
											</c:otherwise>
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
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					
					
				</td>
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
			$("#memberJoinForm").submit();
		}
		
		//跳转到加盟查看页面
		function viewMemberJoin(id){
			 top.jzts();
			 window.location.href = "<%=basePath%>back/toMemberJoin.do?id="+id+"&tm="+new Date().getTime();
		}		
		
		//修改
		function editMemberJoin(id){
			 top.jzts();
			 window.location.href = "<%=basePath%>back/toMemberJoinEdit.do?id="+id+"+&tm="+new Date().getTime();
		}

		</script>
		
		
	</body>
</html>

