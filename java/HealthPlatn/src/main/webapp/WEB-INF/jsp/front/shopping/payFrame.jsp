<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	
	

</script>
	<iframe id="contentFrame" width="100%" height="510" name="contentFrame" src="web/member/toPayFrameSub" style="border: 1px red solid">
				您的浏览器不支持嵌入式框架，或者当前配置为不显示嵌入式框架。
	</iframe>
	<input id="address" type="hidden" value="${address }">
	<input id="total" type="hidden" value="${totalMoney }">
	
<!-- 	${pageContent } -->
<!-- 	<input id="contentInp" value='${pageContent }'  width="600px"> -->
	
<script type="text/javascript">
// $(function(){
// 	alert(111);
// 	alert($("#contentInp"));
// 	var contentstr = $("#contentInp").val();
// 	alert(contentstr);
// 	$("#contentFrame").contents().find("body").html(contentstr);
// });

</script>
<!-- 	<form target="contentFrame" id="punchout_form" name="punchout_form" method='post' action='https://openapi.alipaydev.com/gateway.do?sign=ZHT2NL6uNO6kVukthB9V4%2BoDSAP2pArQHVxRJsUs8xxTiGUi0G1cxIPxY%2By5PxiIgrx64on2cQSjEvJ1T%2B08FK%2BEqj03R43ZA5JCA9g%2BqvHOz0r6a%2FFUEB1%2B1yEQwEBuMSuKbQ6egjRDBpWV%2BI3NaR90b2hZG%2BKUNo%2FzMZHk%2BFFz7VD4EcBL6hr6vZkqamzm9%2B2LY0HJSCs9XLyAVNjLexIfPkbkA7i%2BUPZCZxwxkhevodZ0Cop7b31dfe63qQGF7fIJL7ul5LtBTS0edavdEUcVtgaIQOiiUlc4kd%2FHHusOIhBr4z8HsCiJeA7MZ4PmJHlPDX2Cpbj8UPRNXsVeKw%3D%3D&timestamp=2017-11-17+14%3A37%3A45&sign_type=RSA2&notify_url=http%3A%2F%2F172.20.10.3%3A8080%2FHealthPlatn%2Ffront%2Fshopping%2Fnotify_url.jsp&charset=utf-8&app_id=2016081600258382&method=alipay.trade.page.pay&return_url=http%3A%2F%2F172.20.10.3%3A8080%2FHealthPlatn%2Ffront%2Fshopping%2Freturn_url.jsp&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json'> -->
<!-- <input type='hidden' name='biz_content' value='{&quot;out_trade_no&quot;:&quot;3494667112304989&quot;,&quot;total_amount&quot;:&quot;320.00&quot;,&quot;subject&quot;:&quot;??3494667112304989&quot;,&quot;body&quot;:&quot;&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}'> -->
<!-- <input type="button" id="payButton" value='立即支付' onclick="clickSubmit()"> -->
<!-- </form> -->
<!-- <script type="text/javascript"> -->
<!-- 	$("#punchout_form").submit(); -->
<!-- </script> -->
	
<%-- ${pageContent } --%>
<!-- <form target="contentFrame" id="punchout_form" name="punchout_form" method="post" action="https://openapi.alipaydev.com/gateway.do?sign=bDgJMCRj7yHurXbHhkXojScvNaudzvgpDAdXrYfTL2bSaYA9iJ%2Bjc9SctIvypDbbIEixPct01gpdcXM20pSvWjdpa5b%2F2%2BiXrha9XH737LxpxYzdgASMLeQM%2BKsAjqzG8FQvHYSF2EBy7sdYvy5PNByaNhCnK8Y2R55o2J%2B8vvJ3u1Yc3%2FDAqr1NlVyoXilgqzj%2B5so7OF1aL%2BcPtgyj%2Bfgrzz0S0Y%2FaR1qk%2BZjb0RVdCDmp2ZgOi6yV1%2Bt7C%2B6HVIgaMtLDRcD%2BJ161UoGOy2dSxK%2B9fQQ2ylToVQKNn%2B3SW9D5C%2FJichn7ENnBNAxjwU1Ic2%2BaBxsV0m5dlEAmMw%3D%3D&timestamp=2017-11-20+15%3A13%3A59&sign_type=RSA2&notify_url=http%3A%2F%2F172.20.10.3%3A8080%2FHealthPlatn%2Ffront%2Fshopping%2Fnotify_url.jsp&charset=utf-8&app_id=2016081600258382&method=alipay.trade.page.pay&return_url=http%3A%2F%2F172.20.10.3%3A8080%2FHealthPlatn%2Ffront%2Fshopping%2Freturn_url.jsp&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json"> -->
<!-- <input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;6991207112374393&quot;,&quot;total_amount&quot;:&quot;320.00&quot;,&quot;subject&quot;:&quot;yuanbang6991207112374393&quot;,&quot;body&quot;:&quot;&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}"> -->
<!-- <input type="submit" value="立即支付" style="display:none" > -->
<!-- </form> -->
<!-- <script>document.getElementById("punchout_form").submit();</script> -->