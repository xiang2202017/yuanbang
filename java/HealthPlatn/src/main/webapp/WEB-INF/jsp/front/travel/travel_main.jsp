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

<script type="text/javascript">
	var prePMenuId = "";
	
	//产品类别菜单点击
	function openTravelMenu(id){
		var preMenuAid;
		var menuAid = "menu" + id;
		if(prePMenuId == ""){
			prePMenuId = $("#firstMenu").val();
		}
		preMenuAid = "menu" + prePMenuId;
		if(id != prePMenuId){
			$("#"+menuAid).css("font-weight", "bold");
			$("#"+menuAid).css("font-size", "16px");
			$("#"+preMenuAid).css("font-weight", "normal");
			$("#"+preMenuAid).css("font-size", "15px");
		}
		prePMenuId = id;
		$("#typeIdInp").val(id);
		getDetail(id);
	}
	
	//获取产品详情
	function getDetail(travelId){
		var urlstr = "<%=basePath%>web/toTravelView.do";
		$.ajax({
            url:urlstr,
            data:{"travelId":travelId},
            dataType:"html",
            type:"post",
            success:function(data){
            	//alert(data);
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
	

</script>

<!-- Section -->
	<section id="content">
		<section class="section full-width-bg gray-bg">
			<div class="row">
			
				<!-- Sidebar -->
				<div class="col-lg-3 col-md-3 col-sm-4 sidebar">
					
					
					<!-- Top Rated Products -->
					<div class="sidebar-box white animate-onscroll">
						<input type="hidden" id="typeIdInp" value="${pd.typeId }" >
						<ul class="shop-items-widget">
							<c:forEach items="${typeList}" var="menu" varStatus="vs">
								<li>
									<div class="shop-item-content">
										<h6>${menu.name }</h6>
									</div>
								</li>
									<c:if test="${menu.sub != null && fn:length(menu.sub) != 0 }">
											<c:forEach items="${menu.sub }" var="sub" varStatus="svs">
												<c:choose>
													<c:when test="${vs.index == 0 && svs.index == 0 }">
														<li style="text-indent: 20px;">
															<input type="hidden" id="firstMenu" value="${sub.id }">
															<div class="shop-item-content">
																<h6><a id="menu${sub.id }" style="cursor: pointer;font-weight: bold;font-size: 16px" onclick="openTravelMenu('${sub.id}')">${sub.name }</a></h6>
															</div>
														</li>
													</c:when>
													<c:otherwise>
														<li style="text-indent: 20px">
															<div class="shop-item-content">
																<h6><a id="menu${sub.id }" style="cursor: pointer;" onclick="openTravelMenu('${sub.id}')">${sub.name }</a></h6>
															</div>
														</li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
									</c:if>
								
							</c:forEach>
						</ul>
						
					</div>
					
					
					<!-- /Instagram Photos -->						<!-- Top Rated Products -->
					<div class="sidebar-box white animate-onscroll">
						
						<h3>加盟热线</h3>
						
						<div id="phoneF">
							${travel != null ? travel.joinPhone : ''}
						</div>
						
					</div>
					<!-- /Top Rated Products -->
					
				</div>
				<!-- /Sidebar -->
				
				<div class="col-lg-9 col-md-9 col-sm-8">
						<div id="listDiv">
							<jsp:include page="travel_view.jsp"></jsp:include>
						</div>
				</div>			
			</div>
		</section>
	</section>
