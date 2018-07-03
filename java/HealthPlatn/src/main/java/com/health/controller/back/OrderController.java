package com.health.controller.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.OrderService;
import com.health.service.system.RefundService;
import com.health.system.tools.logistical.LogisticsUtils;
import com.health.system.util.Const;
import com.health.system.util.PageData;

/**
 * 订单后台管理
 * @author XiangYu
 *
 */
@Controller
public class OrderController extends BaseController{
	@Resource(name="orderService")
	private OrderService orderService;
	@Resource(name="refundService")
	private RefundService refundService;

//////////////////////////////////////////////////////////////////////待处理订单//////////////////////////////////////////////////	
	/**
	 * 跳转到订单页面，订单管理默认显示未处理订单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/back/todoOrders"})
	public String listOrder(Page page) throws Exception
	  {
		PageData pd = getPageData();
		Object status = pd.get("status");
		if(status == null || status.equals("")){//所有
	    	pd.put("status", "and ((a.status = 2 or c.status = 1 or c.status = 2)");
	    }else if(status.toString().equals("2")){//待发货
	    	pd.put("hasRefund", "2");
	    	pd.put("status", "and a.status = 2");
	    }else{//待退货
	    	pd.put("status", "and (a.status = 2 and c.status = 1 or c.status = 2)");
	    }
	    SecurityUtils.getSubject().getSession().setAttribute("menutype", "13");
	    return "redirect:/back/showListOrders"; 
	  }
	  
	/**
	 * 跳转到订单更新页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/back/toOrderEdit"})
	  public ModelAndView toEdit(@ModelAttribute("id") String id ) throws Exception
	  {
	    ModelAndView mv = new ModelAndView();
	    
	    //String id = this.getPageData().get("id").toString();
	    
	    try
	    {
	      PageData order = orderService.getOrderById(id);
	      List<PageData> productlist = new ArrayList<PageData>();
	      productlist = this.orderService.getDetailist(id);
	      mv.addObject("productlist", productlist);
	      mv.addObject("order", order);
	      mv.setViewName("back/orders/order_edit");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      this.logger.error(e.toString(), e);
	    }
	    return mv;
	  }
	
	 /**
	   * 发货，物流单号更新
	   * @return
	   * @throws Exception
	   */
	  @RequestMapping({"/back/updateOrder"})
	  public String updateOrder() throws Exception
	  {
	    PageData pg = new PageData();
	    try
	    {
	      pg = getPageData();
	      pg.put("status", 3);
	      pg.put("sendTime", new Date());
	      orderService.updateOrder(pg);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      this.logger.error(e.toString(), e);
	    }
	    return "redirect:/back/showListOrders";
	  }
	  
	  /**
	   * 跳转到退款处理页面
	   * @return
	   * @throws Exception
	   */
	  @RequestMapping({"/back/dealRefund"})
	  public ModelAndView dealRefund() throws Exception
	  {
		  ModelAndView mv = new ModelAndView();
		  String refundId = this.getRequest().getParameter("refundId");
		  PageData refund = refundService.getRefundById(refundId);
		  try{
			mv.addObject("refund", refund);
			mv.setViewName("back/orders/refund_edit");
		  }catch (Exception e) {
			e.printStackTrace();
		  }
		  return mv;
	  }
	  
	  /**
	   * 退款信息更新
	   * @return
	   * @throws Exception
	   */
	  @RequestMapping({"/back/updateRefund"})
	  public String updateRefund(RedirectAttributes attributes) throws Exception
	  {
	    PageData pg = new PageData();
	    try
	    {
	      pg = getPageData();
	      
	      String refundId = pg.getString("id");
	      PageData refund = refundService.getRefundById(refundId);
	      String orderId = refund.get("orderId").toString();
	      String status = refund.get("status").toString();		//退款状态
	      String result = null;		//退款申请处理
	      if(pg.get("result") != null){
	    	  result = pg.get("result").toString();
	      }
	    	  
	      if(status.equals(Const.REFUND_STATUS_REFUND)){//如果是退款申请
	    	  if(result.equals("1")){
	    		  status = Const.REFUND_STATUS_REFUNDED;
	    		  pg.put("status", Const.REFUND_STATUS_REFUNDED);
	    	  }
	      }else if(status.equals(Const.REFUND_STATUS_GOODS_SEND)){//或者货物已签收
	    	  status = Const.REFUND_STATUS_REFUNDED;
	    	  pg.put("status", Const.REFUND_STATUS_REFUNDED);
	      }
	      refundService.updateRefund(pg);
	      
	      if(status.equals(Const.REFUND_STATUS_REFUNDED)){//如果退款成功，代表退款或退款退货流程完成
	    	  //查找订单中是否还有退款及退款退货申请，没有，就更改order订单表中的hasRefund状态
	    	  List<PageData> productlist = orderService.getOrderDetailByOrderId(orderId);
	    	  int refundedNum = 0;		//申请退款已完成的商品数量
	    	  int refundNum = 0;		//正在申请退款的商品
	    	  for(PageData product : productlist){
	    		  if(product.get("refundId") != null){
	    			  PageData fund = refundService.getRefundById(product.get("refundId").toString());
	    			  if(fund.get("status").toString().equals(Const.REFUND_STATUS_REFUNDED)){
	    				  refundedNum++;
	    			  }else{
	    				  if(fund.get("result") != null && !fund.get("result").equals("2")){
	    					  refundNum++;
	    				  }
	    			  }
	    		  }
	    	  }
	    	  PageData order = new PageData();
	    	  boolean needUpdate = false;
	    	  order.put("id", orderId);
	    	  if(productlist.size() == refundedNum){
	    		  order.put("status", 5);
	    		  order.put("hasRefund", 2);
	    		  needUpdate = true;
	    	  }
	    	  if(refundNum == 0){
	    		  order.put("hasRefund", 2);
	    		  needUpdate = true;
	    	  }
	    	  if(needUpdate)
	    		  orderService.updateOrder(order);
	      }
	      attributes.addFlashAttribute("id", orderId); 
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      this.logger.error(e.toString(), e);
	    }
	    return "redirect:/back/toOrderEdit";
	  }
///////////////////////////////////////////////////////显示所有订单///////////////////////////////////////////////////	
	/**
	 * 订单显示
	 */
	@RequestMapping({"/back/showListOrders"})
	public ModelAndView showListOrders(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
	    PageData pd = getPageData();
	    String menuType = SecurityUtils.getSubject().getSession().getAttribute("menutype").toString();
	    Object ostatus = pd.get("ostatus");
	    
	    List<PageData> orderlist = null;
	    String orderNo = pd.getString("orderNo");
	    String memberNo = pd.getString("memberNo");
	    String fromTime = pd.getString("fromTime");
	    String toTime = pd.getString("toTime");
	    
	    if ((orderNo != null) && (!"".equals(orderNo)) && ("0".equals(orderNo))) {
	       pd.put("orderNo", orderNo);
	    }
	    if ((memberNo != null) && (!"".equals(memberNo)))
	    {
	    	memberNo = memberNo.trim();
	        pd.put("memberNo", memberNo);
	    }
	    if ((fromTime != null) && (!"".equals(fromTime)))
	    {
	    	fromTime = fromTime.trim();
	        pd.put("fromTime", fromTime);
	    }
	    if ((toTime != null) && (!"".equals(toTime)))
	    {
	    	toTime = toTime.trim();
	        pd.put("toTime", toTime);
	    }
	    page.setPd(pd);
	    
	    if(menuType.equals("14")){//所有订单
	    	if(ostatus != null && !ostatus.equals("")&& ostatus.equals("2")){//查询待发货订单
	    		pd.put("status", 2);
			    pd.put("hasRefund", 2);	//没有退款申请
	    	}else{
	    		pd.put("status", ostatus);
	    	}
	    }else if(menuType.equals(Const.MENU_ORDER_TOPAY)){//买家未付款订单
	    	pd.put("status", 1);
	    }else if(menuType.equals(Const.MENU_ORDER_TORECEIVE)){//待收货订单
	    	pd.put("status", 3);
	    }else if(menuType.equals(Const.MENU_ORDER_FINISHED)){//已完成的订单
	    	pd.put("status", 4);
	    }else if(menuType.equals(Const.MENU_ORDER_CLOSED)){//已关闭订单
	    	pd.put("status", 5);
	    }else if(menuType.equals(Const.MENU_ORDER_TODO)){//待处理订单
	    	if(ostatus == null || ostatus.equals("")){//所有待处理（包括待发货和待退款）
	    		pd.put("status", 2);//待发货
	    		pd.put("hasRefund", 2);	//没有退款申请
	    		orderlist = this.orderService.getDatalistPage(page);
	    		List<PageData> list2 = this.orderService.refundlistPage(page);
	    		orderlist.addAll(list2);
		    }else if(ostatus.toString().equals("2")){//待发货
		    	pd.put("status", 2);
			    pd.put("hasRefund", 2);	//没有退款申请
		    }else if(ostatus.toString().equals("3")){//待退货
		    	orderlist = this.orderService.refundlistPage(page);
		    }
	    }
	    
	    if(orderlist == null){
	    	orderlist = this.orderService.getDatalistPage(page);
	    }
	    
	    mv.setViewName("back/orders/order_list");
	    mv.addObject("orderlist", orderlist);
	    mv.addObject("pd", pd);
	    mv.addObject("menuType", menuType);
	    mv.addObject("QX", getHC());
	    return mv;
	}

///////////////////////////////////////////////////////////显示所有订单/////////////////////////////////////////////////////
	/**
	 * 所有订单
	 */
	@RequestMapping({"/back/listOrders"})
	public String allListOrders(Page page) throws Exception{
		SecurityUtils.getSubject().getSession().setAttribute("menutype", Const.MENU_ORDER_ALL);
	    return "redirect:/back/showListOrders"; 
	}
	
	
///////////////////////////////////////////////////////////显示未付款订单//////////////////////////////////////////////////
	/**
	 * 待付款订单
	 */
	@RequestMapping({"/back/topayOrders"})
	public String toPayListOrders(Page page) throws Exception{
		SecurityUtils.getSubject().getSession().setAttribute("menutype", Const.MENU_ORDER_TOPAY);
	    return "redirect:/back/showListOrders";
	}
	
///////////////////////////////////////////////////////////显示待收货订单//////////////////////////////////////////////////
	/**
	 * 待收货订单
	 */
	@RequestMapping({"/back/toreceivedOrders"})
	public String toreceivedOrders(Page page) throws Exception{
		SecurityUtils.getSubject().getSession().setAttribute("menutype", Const.MENU_ORDER_TORECEIVE);
	    return "redirect:/back/showListOrders";
	}
///////////////////////////////////////////////////////////显示已完成订单//////////////////////////////////////////////////
	/**
	 * 交易完成订单
	 */
	@RequestMapping({"/back/finishedOrders"})
	public String finishedOrders(Page page) throws Exception{
		SecurityUtils.getSubject().getSession().setAttribute("menutype", Const.MENU_ORDER_FINISHED);
	    return "redirect:/back/showListOrders";
	}
///////////////////////////////////////////////////////////显示已关闭或已取消订单////////////////////////////////////////////	
	/**
	 * 交易关闭订单
	 */
	@RequestMapping({"/back/closedOrders"})
	public String closedOrders(Page page) throws Exception{
		SecurityUtils.getSubject().getSession().setAttribute("menutype", Const.MENU_ORDER_CLOSED);
	    return "redirect:/back/showListOrders";
	}
	
	/**
	 * 订单详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/back/toOrderView"})
	  public ModelAndView toOrderView() throws Exception
	  {
	    ModelAndView mv = getModelAndView();
	    
	    String id = getRequest().getParameter("id");
	    List<PageData> orderDetails = this.orderService.getDetailist(id);
	    
	    PageData order = this.orderService.getOrderById(id);
	    
	    mv.addObject("order", order);
	    mv.addObject("orderDetails", orderDetails);
	    mv.setViewName("back/orders/order_view");
	    
	    return mv;
	  }
	
	/**
	 * 显示物流信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/back/showLogistics"})
	public ModelAndView showLogistics() throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pg = this.getPageData();
		String logisticsNo = pg.getString("logisticsNo");
		PageData logistics = LogisticsUtils.getLogistics(logisticsNo);
		
		mv.addObject("logistics", logistics);
		mv.setViewName("back/orders/order_logistics");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
}
