package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.News;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("newsService")
public class NewsService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteNewsById(Integer id)
    throws Exception
  {
    this.dao.delete("NewsDetailMapper.delete", id);
    this.dao.delete("NewsMapper.delete", id);
  }
  
  public void deleteAllNews(String[] newsIds)
    throws Exception
  {
    this.dao.delete("NewsDetailMapper.deleteAllNews", newsIds);
    this.dao.delete("NewsMapper.deleteAll", newsIds);
  }
  
  public Integer insertNews(PageData pd)
    throws Exception
  {
    return (Integer)this.dao.save("NewsMapper.save", pd);
  }
  
  public void insertNewsDetail(PageData pd)
    throws Exception
  {
    this.dao.save("NewsDetailMapper.save", pd);
  }
  
  public PageData getNewsById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("NewsMapper.findById", id);
  }
  
  public PageData getNewsDetailById(String newsId)
    throws Exception
  {
    return (PageData)this.dao.findForObject("NewsMapper.findDetailById", newsId);
  }
  
  public PageData getNewsDetailByPreId(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("NewsMapper.findDetailByPreId", pd);
  }
  
  public PageData getNewsDetailByNextId(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("NewsMapper.findDetailByNextId", pd);
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
public List<PageData> getNewsList(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("NewsMapper.findList", page);
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("NewsMapper.datalistPage", page);
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
public List<News> getNewsForLen(Integer fromIndex, Integer toIndex, Integer type)
    throws Exception
  {
    Map<String, Integer> map = new HashMap<String, Integer>();
    map.put("fromIndex", fromIndex);
    map.put("toIndex", toIndex);
    map.put("type", type);
    return (List)this.dao.findForList("NewsMapper.getNewsForLen", map);
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
public List<News> getNewsForTop(Integer type)
    throws Exception
  {
    Map<String, Integer> map = new HashMap<String, Integer>();
    map.put("type", type);
    return (List)this.dao.findForList("NewsMapper.getNewsForTop", map);
  }
  
  public void updateNews(PageData pd)
    throws Exception
  {
    this.dao.update("NewsMapper.update", pd);
    this.dao.update("NewsDetailMapper.update", pd);
  }
  
  public void updateNewsClickNum(PageData pd)
    throws Exception
  {
    this.dao.update("NewsMapper.updateClickNum", pd);
  }
}
