package com.health.controller.back;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.ProductService;
import com.health.system.util.AppUtil;
import com.health.system.util.Const;
import com.health.system.util.Jurisdiction;
import com.health.system.util.PageData;

@Controller
public class ProductController
  extends BaseController
{
  String menuUrl = "/back/listProduct";
  @Resource(name="productService")
  private ProductService productService;
  
  @RequestMapping({"/back/listProduct"})
  public ModelAndView listProduct(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String typeName = pd.getString("typeName");
    String keywords = pd.getString("keywords");
    String name = pd.getString("name");
    String typeId = pd.getString("typeId");
    if ((typeId != null) && (!"".equals(typeId)) && ("0".equals(typeId))) {
      pd.put("typeId", "");
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
    List<PageData> productList = this.productService.getDatalistPage(page);
    List<PageData> typeList = this.productService.getProductTypeList();
    
    mv.setViewName("back/product/product_list");
    mv.addObject("productList", productList);
    mv.addObject("typeList", typeList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/toProductView"})
  public ModelAndView toProductView()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String id = getRequest().getParameter("id");
    pd = this.productService.getProductDetailById(id);
    
    mv.addObject("pd", pd);
    mv.setViewName("back/product/product_view");
    
    return mv;
  }
  
  @RequestMapping({"/back/toProductAdd"})
  public ModelAndView toAdd()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      List<PageData> typeList = this.productService.getProductTypeList();
      mv.setViewName("back/product/product_add");
      mv.addObject("typeList", typeList);
      mv.addObject("pg", pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/addProduct"})
  public String addProduct()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      Integer typeId = Integer.valueOf(Integer.parseInt(pg.getString("typeId")));
      String typeName = this.productService.getTypeName(typeId);
      pg.put("typeName", typeName);
      pg.put("sellNum", Integer.valueOf(0));
      pg.put("imgPath", pg.getString("mainImgPath"));
      pg.put("saleTime", new Date());
      this.productService.insertProduct(pg);
      pg.put("discription", pg.getString("content"));
      pg.put("product_id", pg.get("id"));
      this.productService.insertProductDetail(pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listProduct";
  }
  
  @RequestMapping({"/back/toProductEdit"})
  public ModelAndView toEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      mv.setViewName("back/product/product_edit");
      pd = getPageData();
      pd = this.productService.getProductDetailById(pd.getString("id"));
      List<PageData> typeList = this.productService.getProductTypeList();
      mv.addObject("typeList", typeList);
      String discription = pd.getString("discription").replace("\"", "'");
      pd.put("discription", discription);
      mv.addObject("pd", pd);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateProduct"})
  public String updateProduct()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      Integer typeId = Integer.valueOf(Integer.parseInt(pg.getString("typeId")));
      String typeName = this.productService.getTypeName(typeId);
      pg.put("typeName", typeName);
      pg.put("imgPath", pg.getString("mainImgPath"));
      pg.put("discription", pg.getString("content"));
      this.productService.updateProduct(pg);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listProduct";
  }
  
  @RequestMapping({"/back/deleteProduct"})
  public void deleteProduct(PrintWriter out)
  {
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (Jurisdiction.buttonJurisdiction(this.menuUrl, "dels")) {
        this.productService.deleteProductById(Integer.valueOf(Integer.parseInt(pd.get("id").toString())));
      }
      out.write("success");
      out.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
  }
	
	/**
	 * 批量删除产品
	 */
	@RequestMapping(value="/back/deleteAllProduct")
	@ResponseBody
	public Object deleteAllProduct() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("News_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){productService.deleteAllProducts(ArrayNews_IDS);}
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	
}
