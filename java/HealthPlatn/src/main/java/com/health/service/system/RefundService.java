package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("refundService")
public class RefundService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteRefundById(Integer id)  throws Exception
  {
    this.dao.delete("RefundMapper.delete", id);
  }
  
  public void deleteAllRefunds(String[] ids) throws Exception
  {
    this.dao.delete("RefundMapper.deleteAll", ids);
  }
  
  public Integer insertRefund(PageData pd) throws Exception
  {
    return (Integer)this.dao.save("RefundMapper.save", pd);
  }
  
  public PageData getRefundById(String id)  throws Exception
  {
    return (PageData)this.dao.findForObject("RefundMapper.findById", id);
  }
  
  public PageData getRefundByRefundNo(String refundno) throws Exception{
	  return (PageData)this.dao.findForObject("RefundMapper.findByRefundNo", refundno);
  }
  
  public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("RefundMapper.datalistPage", page);
  }
  
  public void updateRefund(PageData pd)
    throws Exception
  {
    this.dao.update("RefundMapper.update", pd);
  }
}
