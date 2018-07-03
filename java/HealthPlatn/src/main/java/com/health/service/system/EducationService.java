package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("educationService")
public class EducationService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteEducationById(Integer id)
    throws Exception
  {
    this.dao.delete("EducationDetailMapper.delete", id);
    this.dao.delete("EducationMapper.delete", id);
  }
  
  public void deleteAllEducations(String[] productIds)
    throws Exception
  {
    this.dao.delete("EducationDetailMapper.deleteAllEducations", productIds);
    this.dao.delete("EducationMapper.deleteAll", productIds);
  }
  
  public Integer insertEducation(PageData pd)
    throws Exception
  {
    return (Integer)this.dao.save("EducationMapper.save", pd);
  }
  
  public void insertEducationDetail(PageData pd)
    throws Exception
  {
    this.dao.save("EducationDetailMapper.save", pd);
  }
  
  public PageData getEducationById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("EducationMapper.findById", id);
  }
  
  public List<PageData> getEducationByTypeId(String id)
    throws Exception
  {
    return (List)this.dao.findForList("EducationMapper.findByTypeId", id);
  }
  
  public PageData getEducationDetailById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("EducationMapper.findDetailById", id);
  }
  
  public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("EducationMapper.datalistPage", page);
  }
  
  public void updateEducation(PageData pd)
    throws Exception
  {
    this.dao.update("EducationMapper.update", pd);
    this.dao.update("EducationDetailMapper.update", pd);
  }
  
  public List<PageData> getEducationTypeList()
    throws Exception
  {
    return (List)this.dao.findForList("EducationTypeMapper.listAll", "");
  }
  
  public String getTypeName(Integer id)
    throws Exception
  {
    return (String)this.dao.findForObject("EducationTypeMapper.getNameById", id);
  }
}
