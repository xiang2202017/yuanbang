package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteProductById(Integer id)
    throws Exception
  {
    this.dao.delete("ProductDetailMapper.delete", id);
    this.dao.delete("ProductMapper.delete", id);
  }
  
  public void deleteAllProducts(String[] productIds)
    throws Exception
  {
    this.dao.delete("ProductDetailMapper.deleteAllProducts", productIds);
    this.dao.delete("ProductMapper.deleteAll", productIds);
  }
  
  public Integer insertProduct(PageData pd)
    throws Exception
  {
    return (Integer)this.dao.save("ProductMapper.save", pd);
  }
  
  public void insertProductDetail(PageData pd)
    throws Exception
  {
    this.dao.save("ProductDetailMapper.save", pd);
  }
  
  public PageData getProductById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("ProductMapper.findById", id);
  }
  
  @SuppressWarnings("unchecked")
  public List<PageData> getTopProduct()
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("ProductMapper.findTopProduct", "");
  }
  
  @SuppressWarnings("unchecked")
  public List<PageData> findRelatedProduct(PageData pd)
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("ProductMapper.findRelatedProduct", pd);
  }
  
  public PageData getProductDetailById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("ProductMapper.findDetailById", id);
  }
  
  @SuppressWarnings("unchecked")
  public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("ProductMapper.datalistPage", page);
  }
  
  public void updateProduct(PageData pd)
    throws Exception
  {
    this.dao.update("ProductMapper.update", pd);
    this.dao.update("ProductDetailMapper.update", pd);
  }
  
  @SuppressWarnings("unchecked")
public List<PageData> getProductTypeList()
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("ProductTypeMapper.listAll", "");
  }
  
  public String getTypeName(Integer id)
    throws Exception
  {
    return (String)this.dao.findForObject("ProductTypeMapper.getNameById", id);
  }
}