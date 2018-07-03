package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("travelService")
public class TravelService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteTravelById(Integer id)
    throws Exception
  {
    this.dao.delete("TravelDetailMapper.delete", id);
    this.dao.delete("TravelMapper.delete", id);
  }
  
  public void deleteAllTravels(String[] productIds)
    throws Exception
  {
    this.dao.delete("TravelDetailMapper.deleteAllTravels", productIds);
    this.dao.delete("TravelMapper.deleteAll", productIds);
  }
  
  public Integer insertTravel(PageData pd)
    throws Exception
  {
    return (Integer)this.dao.save("TravelMapper.save", pd);
  }
  
  public void insertTravelDetail(PageData pd)
    throws Exception
  {
    this.dao.save("TravelDetailMapper.save", pd);
  }
  
  public PageData getTravelById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("TravelMapper.findById", id);
  }
  
  public List<PageData> getTravelByTypeId(String id)
    throws Exception
  {
    return (List)this.dao.findForList("TravelMapper.findByTypeId", id);
  }
  
  public PageData getTravelDetailById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("TravelMapper.findDetailById", id);
  }
  
  public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("TravelMapper.datalistPage", page);
  }
  
  public void updateTravel(PageData pd)
    throws Exception
  {
    this.dao.update("TravelMapper.update", pd);
    this.dao.update("TravelDetailMapper.update", pd);
  }
  
  public List<PageData> getTravelTypeList() throws Exception
  {
    return (List)this.dao.findForList("TravelTypeMapper.listAll", "");
  }
  
  public List<PageData> getTypeDatalistPage(Page page) throws Exception
  {
    return (List)this.dao.findForList("TravelTypeMapper.datalistPage", page);
  }
  
  public Integer insertTravelType(PageData pd) throws Exception
  {
    return (Integer)this.dao.save("TravelTypeMapper.save", pd);
  }
  
  public void updateTravelType(PageData pd) throws Exception
  {
    this.dao.update("TravelTypeMapper.update", pd);
  }
  
  public void deleteTravelTypeById(Integer id) throws Exception
  {
    this.dao.delete("TravelTypeMapper.delete", id);
  }
  
  public void deleteAllTravelTypes(String[] ids) throws Exception
  {
    this.dao.delete("TravelTypeMapper.deleteAll", ids);
  }
  
  public String getTypeName(Integer id)
    throws Exception
  {
    return (String)this.dao.findForObject("TravelTypeMapper.getNameById", id);
  }
}
