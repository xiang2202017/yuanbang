package com.health.controller.front;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.ProductService;
import com.health.system.util.PageData;

@Controller
public class ProductFController
  extends BaseController
{
  @Resource(name="productService")
  private ProductService productService;
  
  @RequestMapping(value={"/web/listProduct"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String listProduct(Page page, ModelMap map)
    throws Exception
  {
    PageData pd = new PageData();
    pd = getPageData();
    
    String typeName = pd.getString("typeName");
    String keywords = pd.getString("keywords");
    String name = pd.getString("name");
    String typeId = pd.getString("type");
    
    List<PageData> typeList = this.productService.getProductTypeList();
    if ((typeId != null) && (!"".equals(typeId)) && (!"0".equals(typeId)))
    {
      pd.put("typeId", typeId);
    }
    else
    {
      PageData type1 = (PageData)typeList.get(0);
      pd.put("typeId", type1.get("id").toString());
    }
    if ((keywords != null) && (!"".equals(keywords)))
    {
      keywords = keywords.trim();
      pd.put("keywords", keywords);
    }
    if ((name != null) && (!"".equals(name)))
    {
      name = name.trim();
      pd.put("name", name);
    }
    if ((typeName != null) && (!"".equals(typeName)))
    {
      typeName = typeName.trim();
      pd.put("typeName", typeName);
    }
    page.setPd(pd);
    page.setShowCount(6);
    List<PageData> productList = this.productService.getDatalistPage(page);
    List<PageData> topList = this.productService.getTopProduct();
    
    map.put("productList", productList);
    map.put("typeList", typeList);
    map.put("topList", topList);
    map.put("pd", pd);
    return "front/product/product_main";
  }
  
  @RequestMapping(value={"/web/getProductList"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String getProductList(Page page, ModelMap map)
    throws Exception
  {
    PageData pd = new PageData();
    pd = getPageData();
    
    String typeName = pd.getString("typeName");
    String keywords = pd.getString("keywords");
    String name = pd.getString("name");
    String typeId = pd.getString("type");
    if ((typeId != null) && (!"".equals(typeId)) && (!"0".equals(typeId))) {
      pd.put("typeId", typeId);
    }
    if ((keywords != null) && (!"".equals(keywords)))
    {
      keywords = keywords.trim();
      pd.put("keywords", keywords);
    }
    if ((name != null) && (!"".equals(name)))
    {
      name = name.trim();
      pd.put("name", name);
    }
    if ((typeName != null) && (!"".equals(typeName)))
    {
      typeName = typeName.trim();
      pd.put("typeName", typeName);
    }
    page.setPd(pd);
    page.setShowCount(6);
    List<PageData> productList = this.productService.getDatalistPage(page);
    
    map.put("productList", productList);
    map.put("pd", pd);
    return "front/product/product_list";
  }
  
  @RequestMapping(value={"/web/toProductView"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String toProductView(ModelMap mv)
    throws Exception
  {
    PageData pd = new PageData();
    pd = getPageData();
    
    String id = getRequest().getParameter("productId");
    pd = this.productService.getProductDetailById(id);
    
    mv.put("pd", pd);
    
    return "front/product/product_view";
  }
}
