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
<script type="text/javascript" src="<%=basePath%>plugins/ueditor3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" >
		
		function formSubmit(){
			var error = "";
			var name = $("#name").val();
			var keywords = $("#keywords").val();
			if(name.length == 0 ){
				error += "标题不能为空\n";
			}
			if(keywords.length == 0){
				error += "关键字不能为空\n";
			}
			//alert(error);
			if(error.length != 0){
				bootbox.alert(error);
				return;
			}
			var txt = UE.getEditor('editor').getContentTxt();
			//var txt = UE.getEditor('editor').getContentTxt();
			$("#contentTxt").val(txt);
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
							<h4 class="lighter">添加教育品牌</h4>
						</div>
						<div class="widget-body">


							<div class="widget-main">
								<div class="step-content row-fluid position-relative">

									<form action="<%=basePath%>back/addEducation" name="productForm" id="productForm" method="post">
										<div id="zhongxin">
											<table style="width:100%;" id="xtable">
												<tr>
													<td align="right">教育品牌名</td>
													<td style="margin-top:0px;">
														<input type="text" name="name" id="name" />
													</td>
												</tr>
												<tr>
													<td width="20%" align="right">教育品牌分类</td>
													<td>
														
														<select name="typeId" id="typeId">
															<c:forEach items="${typeList }" var="item" varStatus="vs">
																<option value="${item.id }" >${item.name }</option>   
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right">搜索关键词</td>
													<td style="margin-top:0px;">
														<input type="text" name="keywords" id="keywords" />
													</td>
												</tr>
												<tr>
													<td align="right">加盟热线</td>
													<td>
														 <input type="text" name="joinPhone" id="joinPhone" />
													</td>
												</tr>
												<tr>
													<td align="right" valign="top">教育品牌描述</td>
													<td id="nr">
														<input type="hidden" id="contentTxt" name="contentTxt" />
														<!-- 加载编辑器的容器 -->
													    <script id="editor" name="content" type="text/plain" style="width:92%;height:500px;">
        														
    													</script>
<!-- 														<script id="editor" type="text/plain" style="width:96%;height:259px;"></script> -->
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

	<!-- 编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor3/";</script>
<!-- 	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script> -->
<!-- 	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script> -->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>plugins/ueditor3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>plugins/ueditor3/ueditor.all.min.js"> </script>
	<!-- 编辑框-->
	

	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/bootbox.js"></script>
	<!--引入属于此页面的js -->
	<script type="text/javascript" src="static/js/myjs/toolEmail.js"></script>
	<script type="text/javascript" src="static/js/myjs/image.js"></script>
</body>
</html>

