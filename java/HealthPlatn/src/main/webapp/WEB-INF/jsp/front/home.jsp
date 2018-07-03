<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
		//跳转到资讯详情
		function getDetail_home(newsId){
			var urlstr = "<%=basePath%>web/newsList.do";
			$.ajax({
	            url:urlstr,
	            data:{"newsId":newsId, "menuId":2},//对应的菜单为资讯
	            dataType:"html",
	            type:"post",
	            success:function(data){
	                //div加载页面
	                $("#mfDiv").html(data);
	            }
	        });
		}
</script>

	<!-- Container -->
<!-- 	<div class="container"> -->
		<section id="content">
			
			<!-- Section -->
			<section class="section full-width-bg">
				
				<div class="row">
				
					<div class="col-lg-12 col-md-12 col-sm-12">
						
						<!-- Revolution Slider -->
						<div class="tp-banner-container main-revolution">
						
							<span class="Apple-tab-span"></span>
 
							<div class="tp-banner">
								
								<ul>
									<li data-transition="papercut" data-slotamount="7">
										<img src="<%=basePath %>static/front_UI/img/timg1.jpg" alt="">
										<div class="tp-caption"  data-x="100" data-y="115" data-speed="700" data-start="1000" data-easing="easeOutBack"><h2>源邦集团<br>只为给你更健康的生活</h2></div>
<!-- 										<div class="tp-caption"  data-x="100" data-y="310" data-speed="500" data-start="1200" data-easing="easeOutBack"><a href="#" class="button big"></a></div> -->
									</li>
									
									<li data-transition="papercut" data-slotamount="7">
										<img src="<%=basePath %>static/front_UI/img/timg2.jpg" alt="">
										<div class="tp-caption align-center" data-x="center" data-y="135" data-speed="700" data-start="1000" data-easing="easeOutBack"><h4 class="great-vibes">源邦集团</h4></div>
										<div class="tp-caption align-center" data-x="center" data-y="220" data-speed="500" data-start="1200" data-easing="easeOutBack"><h2>邀您共创未来</h2></div>
<!-- 										<div class="tp-caption align-center" data-x="center" data-y="300" data-speed="300" data-start="1400"><a href="#" class="button big button-arrow"></a></div> -->
									</li>
									
									<li data-transition="papercut" data-slotamount="7">
										<img src="<%=basePath %>static/front_UI/img/timg2.jpg" alt="">
										<div class="tp-caption align-right" data-x="right" data-hoffset="-100" data-y="150" data-speed="700" data-start="1000" data-easing="easeOutBack"><h2>我们拥有经验</h2></div>
										<div class="tp-caption align-right" data-x="right" data-hoffset="-100" data-y="225" data-speed="500" data-start="1200" data-easing="easeOutBack"><p>你们拥有梦想<br> </p></div>
<!-- 										<div class="tp-caption align-right" data-x="right" data-hoffset="-100" data-y="305" data-speed="300" data-start="1400"><a href="#" class="button big button-arrow"></a></div> -->
									</li>
								</ul>
								
							</div>
						 
						</div>
						<!-- /Revolution Slider -->
						
					</div>
				</div>	
				
				<!-- 公司简介 -->
				<div class="row">
						
					<div class="col-lg-12 col-md-12 col-sm-12">
				
						<h3 class="animate-onscroll no-margin-top">公司简介</h3>
						
						<p class="animate-onscroll">陕西源邦实业集团有限公司成立于2000年08月04日，类型为有限责任公司（自然人投资或控股），注册号916100007197347181，注册资本人民币伍仟万元，注册法人王源，地址为西安市碑林区东大街119号。公司经营范围包括：办公及酒店家具的生产与销售；纯水净化设备的研发、生产与销售、水循环净化工程的设计与施工；空气净化工程及环保节能项目研发与施工；房地产开发项目咨询；室内外装饰装修设计与施工；园林绿化工程、水电安装工程、楼宇智能化、亮化及消防工程；通信工程、通讯设备、电子产品的销售及网络技术研究开发、科技信息咨询；酒店管理、连锁餐饮管理；服装类及包装食品批发与销售；企业营销策划、广告宣传推广服务等。</p>
						<p class="animate-onscroll">经过多年的发展，陕西源邦实业集团有限公司已经成长成为地方行业领军企业，积累了雄厚的资本和技术力量，有一只成熟的经营管理团队。为拓展业务范围有效分散企业经营风险，从而提升企业效益，同时也是为了积极响应政策号召，承担企业的社会责任，陕西源邦实业集团有限公司积极把握政策导向和西安优越位置，从非转基因大豆的全产业开发入手，主导与俄罗斯“比金”农场、九三集团、鲜豆家豆制品速成设备厂等企业开展合作，启动“逗事”非转基因大豆全产业链健康工程项目建设工作，为我国居民提供纯正、安全、营养、绿色的非转基因大豆及产品，从而以综合优势介入我国营养健康产业链。</p>
						<p class="animate-onscroll">我们的优势</p>
						<p class="animate-onscroll">OUR ADVANTAGE--合作体企业的品牌实力 </p>
						<p class="animate-onscroll">哈尔滨九三粮油工业集团</p>
						<p class="animate-onscroll">台湾快乐玛丽安教育集团</p>
						<p class="animate-onscroll">香港玖玲国际生命科学集团</p>
						<p class="animate-onscroll">俄罗斯波罗的海农工投资公司</p>
						<p class="animate-onscroll">北京喜多屋餐饮管理公司</p>
						<p class="animate-onscroll">上海鲜豆家科技有限公司</p>
						<p class="animate-onscroll">上海市女子财富学院</p>
						
						 <p class="animate-onscroll">强大的科研</p>
						<p class="animate-onscroll">科研基地拥有北京大学、武汉大学、长沙国防科技大学，西安交通大学，上海复旦大学，俄罗斯彼得圣堡大学，台湾大学，加拿大圭尔夫大学等国内外知名高校的6个博士后科技人才与各板块专业领域技能型人才进行项目和产品的研发以及模式的创新。</p>
						<p class="animate-onscroll">1、优秀的专业运营团队</p>
						<p class="animate-onscroll">我们拥有一支平均从事健康行业超过十年的优秀运营团队，能够聚焦与细分当前5000亿的国内保健品市场，带领志同道合者走向成功。</p>
						<p class="animate-onscroll">2、我们拥有台湾快乐玛丽安教育集团直接导入已运营20多年幼儿园的核心专业团队，可全盘无缝式输出快乐玛丽安体验式的教育模式和0-18岁的所有教程！</p>
						<p class="animate-onscroll">3、我们拥有强大的一线明星团队为集团各板块项目及产品进行推广代言。</p>
						<p class="animate-onscroll">国家政策机会</p>
						<p class="animate-onscroll">1、健康板块响应2016年的中央一号文件保障居民食品安全，从田间到舌间，提倡我国农业“走出去、引进来”发展政策的需要，确切落实国家“一带一路”发展战略，将“走出去”和“引进来”有机结合，通过高效的市场拓展及独特的市场模式，实现大健康行业的健康目标。</p>
						<p class="animate-onscroll">2、教育板块响应国家号召、发展创新性基础教育的新平台，抓住国家大力践行供给侧改革和鼓励引导互联网+发展契机，通过“健康教育产业链投资平台+学前教育及培训+国际贸易+有机食品+黑科技产品+连锁健康美食”的运营模式，规模化线上线下推广健康教育全产业链平台孵化功能，最终达到项目在现代商业和互联网金融下的推动辐射各行各业，并达到融资上市目的，实现国有、外企，民营、社会资本的有机结合，打造全新的健康、教育行业实现互联网+的新概念，树立行业标杆。</p>
						
						<p class="animate-onscroll">我们的愿景</p>
						
						<p class="animate-onscroll">在中国经济进入新常态的大背景下，源邦实业集团旗下【太狸生物】，【太狸教育】【太狸和食】【太狸旅游】以"把一生一世的生意做成一生一世的事业"为理念，响应国家创新驱动发展战略，全新打造一个新型健康教育全产业链投资平台。</p>

					
					</div>
							
				</div>
			</section>
			<!-- selection -->
			
			<!-- Section -->
			<section class="section full-width-bg gray-bg">
				
				<div class="row">
				
					<div class="col-lg-9 col-md-9 col-sm-8">
						
						<h3 class="animate-onscroll no-margin-top">公司资讯</h3>
						<!-- 公司资讯 -->
						<c:forEach items="${fpd.comNews }" var="item">
							<div class="blog-post big animate-onscroll">
								
								<div class="post-image">
									<img src="${item.imgPath }" alt="" width="350" height="230">
								</div>
								
								<h5 class="post-title"><a onclick="getDetail_home('${item.id}')">${item.title }</a></h5>
								
								<div class="post-meta">
									<span>${item.editime != null ? item.editime : item.creatime}</span>
								</div>
								
								<p>${item.remark }...</p>
								
								<a onclick="getDetail_home('${item.id}')" class="button read-more-button big button-arrow">更多</a>
								
							</div>
						</c:forEach>
						<!-- /Blog Post -->						
					</div>
					
					
					
					<!-- Sidebar -->
					<div class="col-lg-3 col-md-3 col-sm-4 sidebar">
						
												<!-- Upcoming Events -->
						<div class="sidebar-box white animate-onscroll">
							<h3>资讯点击排行榜</h3>
							<ul class="upcoming-events">
								<c:forEach items="${fpd.comNews }" var="item" varStatus="vs">
									<c:if test="${vs.index < 5}">
										<!-- Event -->
										<li>
	<!-- 										<div class="date"> -->
	<!-- 											<span> -->
	<!-- 												<span class="month">${item.editime != null ? item.editime : item.creatime}</span> -->
	<!-- 											</span> -->
	<!-- 										</div> -->
											
											<div class="event-content">
												<h6><a onclick="getDetail_home('${item.id}')">${item.title }</a></h6>
												<ul class="event-meta">
													<li><i class="icons icon-clock"></i>${item.editime != null ? item.editime : item.creatime}</li>
	<!-- 												<li><i class="icons icon-location"></i> 340 W 50th St.New York</li> -->
												</ul>
											</div>
										</li>
										<!-- /Event -->
									</c:if>
								</c:forEach>
								
							</ul>
<!-- 							<a href="#" class="button transparent button-arrow">More events</a> -->
						</div>
						<!-- /Upcoming Events -->						
					</div>
					<!-- /Sidebar -->
					
				</div>
				
				
				
				<div class="row no-margin-bottom">
				
					
					<div class="col-lg-12 col-md-12 col-sm-12">
						
												
						<!-- Owl Carousel -->
						<div class="owl-carousel-container">
							
							<div class="owl-header">
								
								<h3 class="animate-onscroll">健康资讯</h3>
								
								<div class="carousel-arrows animate-onscroll">
									<span class="left-arrow"><i class="icons icon-left-dir"></i></span>
									<span class="right-arrow"><i class="icons icon-right-dir"></i></span>
								</div>
								
							</div>
							
							<div class="owl-carousel" data-max-items="3">
								<c:forEach items="${fpd.healthNews }" var="item">
								<!-- Owl Item -->
								<div>
									
									<!-- Blog Post -->
									<div class="blog-post animate-onscroll">
										
										<div class="post-image">
											<img src="${item.imgPath }" alt="" width="350" height="230">
										</div>
										
										<h5 class="post-title"><a onclick="getDetail_home('${item.id}')">${item.title }</a></h5>
										
										<div class="post-meta">
											<span>${item.creatime }</span>
										</div>
										
										<p>${item.remark }...</p>
										
										<a onclick="getDetail_home('${item.id}')" class="button read-more-button big button-arrow">更多</a>
										
									</div>
									<!-- /Blog Post -->
									
								</div>
								<!-- /Owl Item -->
								</c:forEach>
								
							</div>
						
						</div>
						<!-- /Owl Carousel -->						
					</div>
					
					
				
				</div>
				
				
			</section>
			<!-- /Section -->
		
		</section>

			
			<!-- Back To Top -->
			<a href="#" id="button-to-top"><i class="icons icon-up-dir"></i></a>
		
