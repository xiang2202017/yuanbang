package com.health.controller.back;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.TravelService;
import com.health.system.util.AppUtil;
import com.health.system.util.Jurisdiction;
import com.health.system.util.PageData;

@Controller
public class TravelController
  extends BaseController
{
  String menuUrl = "/back/listTravel";
  @Resource(name="travelService")
  private TravelService travelService;
  
  @RequestMapping({"/back/listTravel"})
  public ModelAndView listTravel(Page page)
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
    List<PageData> travelList = this.travelService.getDatalistPage(page);
    List<PageData> typeList = this.travelService.getTravelTypeList();
    
    mv.setViewName("back/travel/travel_list");
    mv.addObject("travelList", travelList);
    mv.addObject("typeList", typeList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/toTravelView"})
  public ModelAndView toTravelView()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String id = getRequest().getParameter("id");
    pd = this.travelService.getTravelDetailById(id);
    
    mv.addObject("pd", pd);
    mv.setViewName("back/travel/travel_view");
    
    return mv;
  }
  
  @RequestMapping({"/back/toTravelAdd"})
  public ModelAndView toAdd()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      List<PageData> typeList = this.travelService.getTravelTypeList();
      mv.setViewName("back/travel/travel_add");
      mv.addObject("typeList", typeList);
      mv.addObject("pg", pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/addTravel"})
  public String addTravel()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      Integer typeId = Integer.valueOf(Integer.parseInt(pg.getString("typeId")));
      String typeName = this.travelService.getTypeName(typeId);
      pg.put("typeName", typeName);
      this.travelService.insertTravel(pg);
      pg.put("content", pg.getString("content"));
      pg.put("travel_id", pg.get("id"));
      this.travelService.insertTravelDetail(pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listTravel";
  }
  
  @RequestMapping({"/back/toTravelEdit"})
  public ModelAndView toEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      mv.setViewName("back/travel/travel_edit");
      pd = getPageData();
      pd = this.travelService.getTravelDetailById(pd.getString("id"));
      List<PageData> typeList = this.travelService.getTravelTypeList();
      mv.addObject("typeList", typeList);
      String content = pd.getString("content").replace("\"", "'");
      pd.put("content", content);
      mv.addObject("pd", pd);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateTravel"})
  public String updateTravel()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      Integer typeId = Integer.valueOf(Integer.parseInt(pg.getString("typeId")));
      String typeName = this.travelService.getTypeName(typeId);
      pg.put("typeName", typeName);
      pg.put("content", pg.getString("content"));
      this.travelService.updateTravel(pg);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listTravel";
  }
  
  @RequestMapping({"/back/deleteTravel"})
  public void deleteTravel(PrintWriter out)
  {
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (Jurisdiction.buttonJurisdiction(this.menuUrl, "dels")) {
        this.travelService.deleteTravelById(Integer.valueOf(Integer.parseInt(pd.get("id").toString())));
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
  
  @RequestMapping({"/back/deleteAllTravel"})
  @org.springframework.web.bind.annotation.ResponseBody
  public Object deleteAllTravel()
  {
	  PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("Travel_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){travelService.deleteAllTravels(ArrayNews_IDS);}
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
  
  ////////////////////////////////////////////////////////旅游品牌类别管理///////////////////////////////////
  @RequestMapping({"/back/listTravelType"})
  public ModelAndView listTravelType(Page page) throws Exception
  {
    ModelAndView mv = getModelAndView();
    
    
    List<PageData> typeList = this.travelService.getTypeDatalistPage(page);
    
    mv.setViewName("back/travel/travel_type_list");
    mv.addObject("travelTypeList", typeList);
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/toTravelTypeAdd"})
  public ModelAndView toAddTravelType() throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      mv.setViewName("back/travel/travel_type_add");
      mv.addObject("pg", pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/addTravelType"})
  public String addTravelType() throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      this.travelService.insertTravelType(pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listTravelType";
  }
  
  @RequestMapping({"/back/toTravelTypeEdit"})
  public ModelAndView toTravelTypeEdit() throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      mv.setViewName("back/travel/travel_type_edit");
      pd = getPageData();
      Integer id = Integer.parseInt(pd.get("id").toString().trim());
      String name = this.travelService.getTypeName(id);
      pd.put("name", name);
      mv.addObject("pd", pd);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateTravelType"})
  public String updateTravelType() throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      this.travelService.updateTravelType(pg);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listTravelType";
  }
  
  @RequestMapping({"/back/deleteTravelType"})
  public void deleteTravelType(PrintWriter out)
  {
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (Jurisdiction.buttonJurisdiction(this.menuUrl, "dels")) {
        this.travelService.deleteTravelTypeById(Integer.valueOf(Integer.parseInt(pd.get("id").toString())));
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
  
  @RequestMapping({"/back/deleteAllTravelType"})
  @org.springframework.web.bind.annotation.ResponseBody
  public Object deleteAllTravelType()
  {
	  PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("TravelType_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){travelService.deleteAllTravelTypes(ArrayNews_IDS);}
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
  
  public Map<String, String> getHC()
  {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    return (Map)session.getAttribute("QX");
  }
}