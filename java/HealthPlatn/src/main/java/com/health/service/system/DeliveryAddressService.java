package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("deliveryAddressService")
public class DeliveryAddressService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteAddressById(Integer id)
    throws Exception
  {
    this.dao.delete("DeliveryAddressMapper.delete", id);
  }
  
  public void deleteAllAddresss(String[] ids)
    throws Exception
  {
    this.dao.delete("DeliveryAddressMapper.deleteAll", ids);
  }
  
  public Integer insertAddress(PageData pd)
    throws Exception
  {
    return (Integer)this.dao.save("DeliveryAddressMapper.save", pd);
  }
  
  public PageData getAddressById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("DeliveryAddressMapper.findById", id);
  }
  
  public List<PageData> getAddressByMemberId(String id)
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("DeliveryAddressMapper.findByMemberId", id);
  }
  
  public PageData findDefaultAddress(String memberId)
    throws Exception
  {
    return (PageData)this.dao.findForObject("DeliveryAddressMapper.findDefaultAddress", memberId);
  }
  
  public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("DeliveryAddressMapper.datalistPage", page);
  }
  
  public List<PageData> findList(String memberId)
    throws Exception
  {
    return (List)this.dao.findForList("DeliveryAddressMapper.findList", memberId);
  }
  
  public void updateAddress(PageData pd)
    throws Exception
  {
    this.dao.update("DeliveryAddressMapper.update", pd);
  }
}