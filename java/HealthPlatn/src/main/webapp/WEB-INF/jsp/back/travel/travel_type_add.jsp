<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<!-- jsp文件头和头部 -->
<%@ include file="../top.jsp"%>

<style type="text/css">
div{
     width:100%;
 }

#uploadDiv {
	float: left;
	width: 100%;
}

#waitDiv {
	position: absolute;
	z-index: 999999;
	width: 100%;
	height: 100%;
}
</style>
<script type="text/javascript" charset="utf-8" >
		
		function formSubmit(){
			var error = "";
			var name = $("#name").val();
			if(name.length == 0 ){
				error += "名称不能为空\n";
			}
			if(error.length != 0){
				bootbox.alert(error);
				return;
			}
			$("#productForm").submit();
	     }
	     
	     function cancel(){
			history.back();
		}
	
	</script>

</head>
<body>

	<div id="waitDiv" style="display: none;background-color: gray;">
		<img src="<%=basePath%>static/img/wait.gif" />
	</div>

	<div class="container-fluid" id="main-container">



		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="span12">
					<div class="widget-box">
						<div
							class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
							<h4 class="lighter">添加旅游品牌类别</h4>
						</div>
						<div class="widget-body">


							<div class="widget-main">
								<div class="step-content row-fluid position-relative">

									<form action="<%=basePath%>back/addTravelType" name="productForm" id="productForm" method="post">
										<div id="zhongxin">
											<table style="width:100%;" id="xtable">
												<tr>
													<td align="right">旅游品牌类别</td>
													<td style="margin-top:0px;">
														<input type="text" name="name" id="name" />
													</td>
												</tr>
												<tr>
													<td id="nr" align="center" colspan="2" >
														<button type="button" onclick="formSubmit()">添加</button>
														<button type="reset" onclick="cancel()">取消</button>
													</td>
												</tr>
											</table>
										</div>
									</form>



								</div>
							</div>
							<!--/widget-main-->
						</div>
						<!--/widget-body-->
					</div>
				</div>



				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i> </a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.7.2.js'>\x3C/script>");</script>
	<script type="text/javascript" src="static/js/ajaxfileupload.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- 引入 -->

	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/bootbox.js"></script>
	<!--引入属于此页面的js -->
	<script type="text/javascript" src="static/js/myjs/toolEmail.js"></script>
	<script type="text/javascript" src="static/js/myjs/image.js"></script>
</body>
</html>

