package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteOrderById(Integer id)
    throws Exception
  {
    this.dao.delete("OrderDetailMapper.delete", id);
    this.dao.delete("OrderMapper.delete", id);
  }
  
  public void deleteAllOrders(String[] ids)
    throws Exception
  {
    this.dao.delete("OrderDetailMapper.deleteAllOrders", ids);
    this.dao.delete("OrderMapper.deleteAll", ids);
  }
  
  public Integer insertOrder(PageData pd)
    throws Exception
  {
    return (Integer)this.dao.save("OrderMapper.save", pd);
  }
  
  public void insertOrderDetail(PageData pd)
    throws Exception
  {
    this.dao.save("OrderDetailMapper.save", pd);
  }
  
  public PageData getOrderById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("OrderMapper.findById", id);
  }
  
  public PageData getOrderDetailById(String id) throws Exception
  {
    return (PageData)this.dao.findForObject("OrderDetailMapper.findById", id);
  }
  
  public PageData getOrderByMemberId(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("OrderMapper.findByMemberId", id);
  }
  
  public PageData getOrderByOrderNo(String orderno) throws Exception{
	  return (PageData)this.dao.findForObject("OrderMapper.findByOrderNo", orderno);
  }
  
  public List<PageData> getOrderDetailByOrderId(String id)
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("OrderDetailMapper.findByNo", id);
  }
  
  public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("OrderMapper.datalistPage", page);
  }
  
  /**
   * 查找订单的详细商品
   * @param orderId
   * @return
   * @throws Exception
   */
  @SuppressWarnings({ "unchecked"})
  public List<PageData> getDetailist(String orderId)
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("OrderMapper.getDetailist", orderId);
  }
  
  public List<PageData> todoDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("OrderMapper.datalistPage", page);
  }
  
  /**
   * 查询待处理的退货退款申请
   * @param page
   * @return
   * @throws Exception
   */
  public List<PageData> refundlistPage(Page page) throws Exception
  {
    return (List)this.dao.findForList("OrderMapper.refundlistPage", page);
  }
  
  /**
   * 查询退货退款申请列表
   * @param orderId
   * @return
   * @throws Exception
   */
  public List<PageData> fefundList(String orderId) throws Exception
  {
    return (List)this.dao.findForList("OrderMapper.fefundList", Integer.parseInt(orderId));
  }
  
  /**
   * 查询退货退款列表（包括申请中的和已经完成的）
   * @param orderId
   * @return
   * @throws Exception
   */
  public List<PageData> refundAllDatalistPage(Page page) throws Exception
  {
    return (List)this.dao.findForList("OrderMapper.refundAllDatalistPage", page);
  }
  
  public void updateOrder(PageData pd) throws Exception
  {
    this.dao.update("OrderMapper.update", pd);
  }
  
  public void updateOrderDetail(PageData pd) throws Exception
  {
    this.dao.update("OrderDetailMapper.update", pd);
  }
}
