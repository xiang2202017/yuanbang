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
	
<div id="waitDiv" style="display: none;background-color: gray;">
	<img src="<%=basePath%>static/img/wait.gif"/>
</div>

<div class="container-fluid" id="main-container">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">
 	<div class="span12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
				<h4 class="lighter">会员续约申请处理</h4>
			</div>
			<div class="widget-body">
			 
				<div class="widget-main">
								<div class="step-content row-fluid position-relative">

									<form action="<%=basePath%>back/updateMemberRenew" name="memberRenewForm" id="memberRenewForm" method="post">
										<input type="hidden" id="step" name="step">
										<div id="zhongxin">
											<table style="width:100%;" id="xtable">
												<tr>
													<td align="right">会员号</td>
													<td style="margin-top:0px;">
													   <input type="hidden" name="id" value="${pd.id }">
													   <input type="hidden" name="memberId" value="${pd.memberId }">
														<input type="text" name="memberNo"  readonly="readonly" id="memberNo" value="${pd.memberNo}"/>
													</td>
												</tr>
												<tr>
													<td align="right">会员姓名</td>
													<td style="margin-top:0px;">
														<input type="text" name="memberName" id="memberName" readonly="readonly" value="${pd.memberName}"/>
													</td>
												</tr>
												<tr>
													<td align="right">申请时间</td>
													<td style="margin-top:0px;">
														<input type="text" name="createTime" id="createTime" readonly="readonly" value="${pd.createTime}"/>
													</td>
												</tr>
												
												<!-- 申请中  处理 -->
												<c:if test="${pd.status == '1' }">
													<tr>
														<td align="right">申请续约时长</td>
														<td>
															 <input type="text" name="requestTerm" id="requestTerm" readonly="readonly" value="${pd.requestTerm}"/>（单位：年）
														</td>
													</tr>
													<tr>
														<td width="20%" align="right">处理意见</td>
														<td>
															<select name="status" id="status">
																<option value="1" >通过</option>  
																<option value="2" >不通过</option>        
															</select>
														</td>
													</tr>
													<tr id="failTr_1" style="display: none;">
														<td width="20%" align="right">失败原因</td>
														<td><textarea id="failReason" name="failReason" rows="5" cols="10"></textarea></td>
													</tr>
													<tr>
														<td></td>
														<td id="nr" align="right">
															<button type="button" onclick="formSubmit(1)">提交</button>
															<button type="reset" onclick="cancel()">取消</button>
														</td>
													</tr>
												</c:if>
												
												<!-- 申请成功  处理 -->
												<c:if test="${pd.status == '2' }">
													<tr>
														<td align="right">续约时长</td>
														<td>
															 <input type="text" name="requestTerm" id="requestTerm" value="${pd.requestTerm}"/>（单位：年）
														</td>
													</tr>
													<tr>
														<td></td>
														<td id="nr" align="right">
															<button type="button" onclick="formSubmit(2)">续约</button>
															<button type="reset" onclick="cancel()">取消</button>
														</td>
													</tr>
												</c:if>
												
											</table>
										</div>
									</form>

								</div>
							</div>
							<!--/widget-main-->
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
		
		<!-- 编辑框-->
		<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.parse.js"></script>
		<!-- 编辑框-->
		
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript" src="static/js/bootbox.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/toolEmail.js"></script>	
		<script type="text/javascript" src="static/js/myjs/image.js"></script>	
		
		<script type="text/javascript">
			function formSubmit(step){
				$("#step").val(step);
				if(step == 1){//申请中
					var status = $("#status").val();
					if(status == 2){//申请失败，需要填写失败原因
						var reason = $("#failReason").val();
						if(reason == ""){
							alert("请填写失败原因!");
							return;
						}
					}
				}
				$("#memberRenewForm").submit();
			}
			
			function cancel(){
				history.back();
			}
			
			//处理结果
			$("#status").change(function(){
				if($(this).val() == 2){
					$("#failTr_1").show();
				}else{
					$("#failTr_1").hide();
				}
			});
		</script>
		
	</body>
</html>

