package com.health.controller.front;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.DeliveryAddressService;
import com.health.service.system.OrderService;
import com.health.service.system.ProductService;
import com.health.service.system.ShoppingCartService;
import com.health.system.tools.pay.alipay.config.AlipayConfig;
import com.health.system.util.Const;
import com.health.system.util.NumberUtil;
import com.health.system.util.PageData;

@Controller
public class ShoppingFController
  extends BaseController
{
  @Resource(name="productService")
  private ProductService productService;
  @Resource(name="shoppingCartService")
  private ShoppingCartService shoppingCartService;
  @Resource(name="deliveryAddressService")
  private DeliveryAddressService deliveryAddressService;
  @Resource(name="orderService")
  private OrderService orderService;
  
  @RequestMapping({"/web/member/toShoppingCart"})
  public String toShoppingCart(ModelMap map, Page page)
    throws Exception
  {
    PageData member = (PageData)SecurityUtils.getSubject().getSession().getAttribute("sessionMember");
    if (member == null) {
      return "front/member/member_login";
    }
    try
    {
      PageData pd = new PageData();
      pd.put("memberNo", member.get("memberNo"));
      page.setPd(pd);
      List<PageData> list = this.shoppingCartService.getDatalistPage(page);
      map.put("productlist", list);
      
      String typeids = "";
      String productIds = "";
      BigDecimal total = new BigDecimal("0.00");
      if (list.size() > 0)
      {
        typeids = "(";
        productIds = "(";
        int i = 0;
        for (PageData item : list)
        {
          BigDecimal price = (BigDecimal)item.get("price");
          BigDecimal num = new BigDecimal(item.get("num").toString());
          total = total.add(price.multiply(num));
          
          PageData product = this.productService.getProductById(item.get("productId").toString());
          String typeId = product.get("typeId").toString();
          Boolean isAdd = Boolean.valueOf(true);
          if ((typeids.indexOf("(" + typeId + ",") > -1) || (typeids.indexOf("," + typeId + ",") > -1) || (typeids.indexOf("," + typeId + ")") > -1)) {
            isAdd = Boolean.valueOf(false);
          } else {
            typeids = typeids + typeId;
          }
          productIds = productIds + product.get("id").toString();
          if (i != list.size() - 1)
          {
            if (isAdd.booleanValue()) {
              typeids = typeids + ",";
            }
            productIds = productIds + ",";
          }
          else
          {
            if (typeids.lastIndexOf(",") == typeids.length() - 1) {
              typeids = typeids.substring(0, typeids.length() - 1);
            }
            typeids = typeids + ")";
            productIds = productIds + ")";
          }
          i++;
        }
      }
      PageData query = new PageData();
      query.put("ids", productIds);
      if(!typeids.equals(""))
    	  query.put("typeIds", "typeId in " + typeids);
      
      List<PageData> relatedList = this.productService.findRelatedProduct(query);
      if (relatedList.size() == 0)
      {
        query.put("typeIds", "typeId not in " + typeids);
        relatedList = this.productService.findRelatedProduct(query);
      }
      map.put("relatedList", relatedList);
      map.put("total", total);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "front/shopping/shopping_cart";
  }
  
  @RequestMapping({"/web/member/addToCart"})
  @ResponseBody
  public Object addToCart()
    throws Exception
  {
    Map<String, String> map = new HashMap();
    try
    {
      String productId = getRequest().getParameter("productId");
      String productNum = getRequest().getParameter("num");
      if ((productNum == null) || (productNum.equals(""))) {
        productNum = "1";
      }
      Integer num = Integer.valueOf(Integer.parseInt(productNum));
      PageData product = this.productService.getProductById(productId);
      PageData member = (PageData)SecurityUtils.getSubject().getSession().getAttribute("sessionMember");
      if (member == null)
      {
        map.put("result", "nologin");
      }
      else
      {
        PageData pd = new PageData();
        pd.put("memberId", member.get("id"));
        pd.put("memberNo", member.get("memberNo"));
        pd.put("phone", member.get("phone"));
        pd.put("status", Integer.valueOf(1));
        pd.put("createTime", new Date());
        pd.put("productId", product.get("id"));
        pd.put("productName", product.get("name"));
        pd.put("productImg", product.get("imgPath"));
        pd.put("price", product.get("price"));
        pd.put("num", num);
        
        this.shoppingCartService.insertShoppingCart(pd);
        map.put("result", "success");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      map.put("result", "fail");
    }
    return map;
  }
  
  @RequestMapping({"/web/member/updateCartItem"})
  @ResponseBody
  public Object updateCartItem() throws Exception
  {
    Map<String, String> map = new HashMap();
    try
    {
    	PageData pg = new PageData();
    	String id = getRequest().getParameter("productId");
    	Object num = getRequest().getParameter("num");
    	pg.put("productId", id);
    	pg.put("num", num);
    	this.shoppingCartService.updateShoppingCart(pg);
    	map.put("result", "success");
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
      map.put("result", "fail");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      map.put("result", "fail");
    }
    return map;
  }
  
  @RequestMapping({"/web/member/delCartItem"})
  @ResponseBody
  public Object delCartItem()
    throws Exception
  {
    Map<String, String> map = new HashMap();
    try
    {
      String id = getRequest().getParameter("id");
      this.shoppingCartService.deleteShoppingCartById(Integer.valueOf(Integer.parseInt(id)));
      map.put("result", "success");
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
      map.put("result", "fail");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      map.put("result", "fail");
    }
    return map;
  }
  
  @RequestMapping({"/web/member/delCart"})
  @ResponseBody
  public Object delCart()
    throws Exception
  {
    Map<String, String> map = new HashMap<String, String>();
    try
    {
      PageData member = (PageData)SecurityUtils.getSubject().getSession().getAttribute("sessionMember");
      Integer memberId = Integer.valueOf(Integer.parseInt(member.get("id").toString()));
      this.shoppingCartService.deleteShoppingCartByMemberId(memberId);
      map.put("result", "success");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      map.put("result", "fail");
    }
    return map;
  }
  
  @RequestMapping({"/web/member/toPayPage"})
  public String toPayPage(ModelMap map) throws Exception
  {
	  Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		PageData member = (PageData)session.getAttribute(Const.SESSION_MEMBER);
		String memberId = member.get("id").toString();
	  
	//获取默认地址defaultAddress
	  PageData defaultAddress = deliveryAddressService.findDefaultAddress(memberId);
	
	//获取地址列表addressList
	  List<PageData> addressList = deliveryAddressService.findList(memberId);
	 
	//获取购物车商品itemList
	  List<PageData> itemList = shoppingCartService.getShoppingCartByMemberId(memberId);
	
	BigDecimal total = new BigDecimal(getRequest().getParameter("total"));
	map.put("total", total);
	map.put("defaultAddress", defaultAddress);
	map.put("addressList", addressList);
	map.put("itemList", itemList);
    return "front/shopping/shopping_cart_toPay";
  }
  
  /**
   * 跳转到支付宝支付的框架页面
   * @param map
   * @return
   * @throws Exception
   */
  @RequestMapping(value="/web/member/toPayFrame")
  public String tozhifubaopay(ModelMap map) throws Exception{
	  map.put("totalMoney", getRequest().getParameter("totalMoney"));
	  map.put("address", getRequest().getParameter("address"));
	  return "front/shopping/payFrame";
  }
  
  /**
   * 跳转的过渡页面
   * @param map
   * @return
   * @throws Exception
   */
  @RequestMapping(value="/web/member/toPayFrameSub")
  public String tozhifubaopay_sub(ModelMap map) throws Exception{
	  
	  return "front/shopping/submitPage";
  }
  
  
  
  /**
   * 开始支付
   * @return
   * @throws Exception
   */
  @RequestMapping(value="/web/member/zhifubaoPay",method = RequestMethod.POST)
  public String zhifubaoPay(HttpServletRequest request, ModelMap map) throws Exception{
	//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		PageData member = (PageData)session.getAttribute(Const.SESSION_MEMBER);
		String orderno = NumberUtil.getOrderNumber(member.get("id").toString());
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(orderno.getBytes("ISO-8859-1"),"UTF-8");
		//付款金额，必填
		String total_amount = new String(request.getParameter("totalMoney").getBytes("ISO-8859-1"),"UTF-8");
		//订单名称，必填
		String subject = new String(("yuanbang"+orderno).getBytes("ISO-8859-1"),"UTF-8");
		//商品描述，可空
		//String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ "" +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//将订单数据存入数据库
		PageData order = new PageData();
		order.put("memberId", member.get("id"));
		order.put("memberNo", member.get("memberNo"));
		order.put("memberName", member.get("memberName"));
		order.put("orderNo", orderno);
		order.put("orderName", "源邦"+orderno);
		order.put("status", 1);//待付款
		order.put("money", request.getParameter("totalMoney"));
		order.put("createTime", new Date());
		order.put("hasRefund", 2);
		
		String addressId = request.getParameter("maddress");
		PageData address = deliveryAddressService.getAddressById(addressId);
		order.put("receiver", address.get("name"));
		order.put("receiverPhone", address.get("phone"));
		order.put("receiverAddress", address.get("address"));
		order.put("desc", "无");
		orderService.insertOrder(order);
		
		//将商品数据存入订单详情表
		List<PageData> itemlist = shoppingCartService.getShoppingCartByMemberId(member.get("id").toString());
		for(PageData item : itemlist){
			PageData orderDetail = new PageData();
			orderDetail.put("orderId", order.get("id"));
			orderDetail.put("orderNo", orderno);
			orderDetail.put("productId", item.get("productId"));
			orderDetail.put("num", item.get("num"));
			orderDetail.put("productName", item.get("productName"));
			orderDetail.put("productImg", item.get("productImg"));
			orderDetail.put("price", item.get("price"));
			orderDetail.put("desc", request.getParameter("desc"));
			orderService.insertOrderDetail(orderDetail);
		}
		
		//删除购物车中对应的记录
		//shoppingCartService.deleteShoppingCartByMemberId(Integer.parseInt(member.get("id").toString()));
		
		//请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		result = result.replace("forms[0]", "getElementByName(\"punchout_form\")");
		//在from表单中加入target属性，指向iframe
		StringBuffer sb = new StringBuffer(result);
		result = sb.insert(6, "target=\"_self\" id=\"punchout_form\" ").toString();//contentFrame
		result = result.replace("<script>document.getElementByName(\"punchout_form\").submit();</script>", "<script>document.getElementById(\"punchout_form\").submit();</script>");
		map.put("pageContent", result);
		
		return "front/shopping/payPrintOut";
  }
  
  /**
   * 支付宝支付同步返回
   * @param request
   * @param map
   * @return
   */
  @RequestMapping(value="/web/member/getPayInfoBack")
  public ModelAndView alipayReturn(HttpServletRequest request, ModelMap map) throws Exception{
	  ModelAndView mv = new ModelAndView();
	  String result = "error";
	  	//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			
			//订单验证
			PageData order = orderService.getOrderByOrderNo(out_trade_no);
			if(order != null && order.get("money").toString().equals(total_amount)){
				result = "验签成功";
			}
		}else {
			result = "验签失败";
		}
		PageData pd = new PageData();
		pd.put("pagePath", "home.jsp");
		mv.addObject("fpd", pd);
        mv.setViewName("/");
	  return mv;
  }
  
  /**
   * 支付宝支付异步返回
   * @param request
   * @param map
   * @return
   */
  @RequestMapping(value="/web/member/getPayInfoAysnBack")
  public void getPayInfoAysnBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
	  String result = "success";
	  	//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

//		trade_status交易状态	
//		TRADE_FINISHED	交易完成	true（触发通知）
//		TRADE_SUCCESS	支付成功	true（触发通知）
//		WAIT_BUYER_PAY	交易创建	false（不触发通知）
//		TRADE_CLOSED	交易关闭	false（不触发通知）
		
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			
			//更改订单的状态
			PageData order = orderService.getOrderByOrderNo(out_trade_no);
			if(order != null && order.get("money").toString().equals(total_amount)){
				order.put("status", 2);//已付款
				order.put("payType", 1);//支付宝
				order.put("payNo", trade_no);
				order.put("payTime", new Date());
				order.put("hasInvoice", 2);
				orderService.updateOrder(order);
			}
		}else {
			result = "fail";
		}
		//——请在这里编写您的程序（以上代码仅作参考）——
	  response.getWriter().println(result);
  }
}
