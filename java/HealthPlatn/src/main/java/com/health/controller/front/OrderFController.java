package com.health.controller.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.OrderService;
import com.health.service.system.RefundService;
import com.health.system.tools.logistical.LogisticsUtils;
import com.health.system.util.Const;
import com.health.system.util.PageData;

@Controller
public class OrderFController extends BaseController{

	@Resource(name="orderService")
	private OrderService orderService;
	@Resource(name="refundService")
	private RefundService refundService;
	
	/**
	 * 跳转到会员订单主页面,默认显示所有订单
	 * @return
	 */
	@RequestMapping(value="/web/member/toMemberOrderMain")
	public String toMemberOrder(Page page, ModelMap map) throws Exception{
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
		
		Integer memberId = Integer.parseInt(member.get("id").toString());
		pd.put("memberId", memberId);
		pd.put("memberNo", member.get("memberNo"));
		
		page.setPd(pd);
		
		return "front/orders/member_order_main";
	}
	
	/**
	 * 跳转到会员订单列表页面
	 * @return
	 */
	@RequestMapping(value="/web/member/getOrderList")
	public String getOrderList(Page page, ModelMap map) throws Exception{
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
		
		Integer memberId = Integer.parseInt(member.get("id").toString());
		pd.put("memberId", memberId);
		pd.put("memberNo", member.get("memberNo"));
		
		String orderType = getRequest().getParameter("type").toString();	//想获取订单的类型
		Integer status = null;
		if(orderType.equals("2")){	//代付款
			status = 1;
		}else if(orderType.equals("3")){//待发货
			status = 2;
		}else if(orderType.equals("4")){//待收货
			status = 3;
		}else if(orderType.equals("5")){//待评价
			status = 4;
		}
		
		//查询会员的全部订单
		List<PageData> orderlist = new ArrayList<PageData>();
		if(orderType.equals("6")){//退款售后
			page.setPd(pd);
			orderlist = orderService.refundAllDatalistPage(page);
		}else{
			pd.put("status", status);
			page.setPd(pd);
			orderlist = orderService.getDatalistPage(page);
		}
		
		for(PageData item :orderlist){
			String orderId = item.get("id").toString();
			//找到对应的订单商品列表
			List<PageData> productlist = orderService.getDetailist(orderId);
			item.put("productlist", productlist);
			
		}
		
		map.put("orderlist", orderlist);
		map.put("member", member);
		return "front/orders/member_order_list";
	}
	
	/**
	 * 跳转到订单详情查看界面
	 * @return
	 */
	@RequestMapping(value="/web/member/getOrderDetail")
	public String getOrderDetail(ModelMap map) throws Exception{
		String orderId = getRequest().getParameter("orderId");
		PageData order = orderService.getOrderById(orderId);
		List<PageData> productlist = orderService.getDetailist(orderId);
		map.put("order", order);
		map.put("productlist", productlist);
		return "front/orders/member_order_detail";
	}
	
	/**
	 * 获取物流信息
	 * @return
	 */
	@RequestMapping(value="/web/member/getLogistics")
	public String getLogistics(ModelMap map) throws Exception{
		String logisticsNo = getRequest().getParameter("logisticNo");
		PageData logistics = LogisticsUtils.getLogistics(logisticsNo);
		
		map.put("logistics", logistics);
		return "front/orders/member_order_logistics";
	}
	
	/**
	 * 删除订单
	 * @return
	 */
	@RequestMapping(value="/web/member/delOrder")
	@ResponseBody
	public Object delOrder() throws Exception{
		String result = "error";
		String id = getRequest().getParameter("orderId");
		try {
			orderService.deleteOrderById(Integer.parseInt(id));
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	/**
	 * 确认收货
	 * @return
	 */
	@RequestMapping(value="/web/member/certReceive")
	@ResponseBody
	public Object certOrder() throws Exception{
		String result = "error";
		String id = getRequest().getParameter("orderId");
		try {
			PageData pd = new PageData();
			pd.put("id", Integer.parseInt(id));
			pd.put("status", 4);
			orderService.updateOrder(pd);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	/**
	 * 跳转到申请退款界面
	 * @return
	 */
	@RequestMapping(value="/web/member/goRefund")
	public String goRefund(ModelMap map) throws Exception{
		String orderId = getRequest().getParameter("orderId");
		String detailId = getRequest().getParameter("detailId");
		
		PageData order = orderService.getOrderById(orderId);
		PageData orderDetail = orderService.getOrderDetailById(detailId);
		map.put("order", order);
		map.put("orderDetail", orderDetail);
		return "front/orders/member_order_refund";
	}
	
	/**
	 * 申请退款
	 * @return
	 */
	@RequestMapping(value="/web/member/refundApply")
	@ResponseBody
	public Object refundApply() throws Exception{
		String result = "error";
		String id = getRequest().getParameter("orderId");
		String orderNo = getRequest().getParameter("orderNo");
		String type = getRequest().getParameter("type");
		String money = getRequest().getParameter("money");
		String reason = getRequest().getParameter("reason");
		String orderDetailId = getRequest().getParameter("orderDetailId");
		try {
			PageData pd = new PageData();
			if(type.equals("1")){
				pd.put("status", 1);
			}else{
				pd.put("status", 2);
			}
			pd.put("orderId", Integer.parseInt(id));
			pd.put("orderNo", orderNo);
			pd.put("money", new BigDecimal(money));
			pd.put("reason", reason);
			pd.put("orderDetailId", orderDetailId);
			pd.put("createTime", new Date());
			//插入退款申请记录
			refundService.insertRefund(pd);
			
			//更新订单相关退款信息
			PageData orderInfo = new PageData();
			orderInfo.put("id", id);
			orderInfo.put("hasRefund", 1);
			orderService.updateOrder(orderInfo);
			
			//更新订单详情相关退款信息
			PageData orderDetailInfo = new PageData();
			orderDetailInfo.put("id", orderDetailId);
			orderDetailInfo.put("refundId", pd.get("id"));
			orderService.updateOrderDetail(orderDetailInfo);
			
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		return obj;
	}
	
	/**
	 * 跳转到退款查看界面
	 * @return
	 */
	@RequestMapping(value="/web/member/getRefundDetail")
	public String getRefundDetail(ModelMap map) throws Exception{
		String orderId = getRequest().getParameter("orderId");
		String detailId = getRequest().getParameter("detailId");
		String refundId = getRequest().getParameter("refundId");
		
		PageData order = orderService.getOrderById(orderId);
		PageData orderDetail = orderService.getOrderDetailById(detailId);
		PageData refund = refundService.getRefundById(refundId);
		map.put("order", order);
		map.put("orderDetail", orderDetail);
		map.put("refund", refund);
		return "front/orders/member_order_refund_logistics";
	}
	
	/**
	 * 退款申请更新
	 * @return
	 */
	@RequestMapping(value="/web/member/refundUpdate")
	@ResponseBody
	public Object refundUpdate() throws Exception{
		String result = "error";
		String id = getRequest().getParameter("refundId");
		String reason = getRequest().getParameter("reason");
		String money = getRequest().getParameter("money");
		String logistics = getRequest().getParameter("logisticsNo");
		try {
			PageData pd = new PageData();
			pd.put("reason", reason);
			if(money != null)
				pd.put("money", new BigDecimal(money));
			if(logistics != null){
				pd.put("logisticsNo", logistics);
				pd.put("sendTime", new Date());
				pd.put("status", Const.REFUND_STATUS_GOODS_SEND);
			}
			pd.put("id", id);
			refundService.updateRefund(pd);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	/**
	 * 退款申请撤销
	 * @return
	 */
	@RequestMapping(value="/web/member/refundDel")
	@ResponseBody
	public Object refundDel() throws Exception{
		String result = "error";
		String id = getRequest().getParameter("refundId");
		try {
			refundService.deleteRefundById(Integer.parseInt(id));
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
}
