package com.health.controller.back;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.EducationService;
import com.health.system.util.AppUtil;
import com.health.system.util.Jurisdiction;
import com.health.system.util.Logger;
import com.health.system.util.PageData;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EducationController
  extends BaseController
{
  String menuUrl = "/back/listEducation";
  @Resource(name="educationService")
  private EducationService educationService;
  
  @RequestMapping({"/back/listEducation"})
  public ModelAndView listEducation(Page page)
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
    List<PageData> educationList = this.educationService.getDatalistPage(page);
    List<PageData> typeList = this.educationService.getEducationTypeList();
    
    mv.setViewName("back/education/education_list");
    mv.addObject("educationList", educationList);
    mv.addObject("typeList", typeList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/toEducationView"})
  public ModelAndView toEducationView()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String id = getRequest().getParameter("id");
    pd = this.educationService.getEducationDetailById(id);
    
    mv.addObject("pd", pd);
    mv.setViewName("back/education/education_view");
    
    return mv;
  }
  
  @RequestMapping({"/back/toEducationAdd"})
  public ModelAndView toAdd()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      List<PageData> typeList = this.educationService.getEducationTypeList();
      mv.setViewName("back/education/education_add");
      mv.addObject("typeList", typeList);
      mv.addObject("pg", pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/addEducation"})
  public String addEducation()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      Integer typeId = Integer.valueOf(Integer.parseInt(pg.getString("typeId")));
      String typeName = this.educationService.getTypeName(typeId);
      pg.put("typeName", typeName);
      this.educationService.insertEducation(pg);
      pg.put("content", pg.getString("content"));
      pg.put("education_id", pg.get("id"));
      this.educationService.insertEducationDetail(pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listEducation";
  }
  
  @RequestMapping({"/back/toEducationEdit"})
  public ModelAndView toEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      mv.setViewName("back/education/education_edit");
      pd = getPageData();
      pd = this.educationService.getEducationDetailById(pd.getString("id"));
      List<PageData> typeList = this.educationService.getEducationTypeList();
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
  
  @RequestMapping({"/back/updateEducation"})
  public String updateEducation()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      Integer typeId = Integer.valueOf(Integer.parseInt(pg.getString("typeId")));
      String typeName = this.educationService.getTypeName(typeId);
      pg.put("typeName", typeName);
      pg.put("content", pg.getString("content"));
      this.educationService.updateEducation(pg);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listEducation";
  }
  
  @RequestMapping({"/back/deleteEducation"})
  public void deleteEducation(PrintWriter out)
  {
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (Jurisdiction.buttonJurisdiction(this.menuUrl, "dels")) {
        this.educationService.deleteEducationById(Integer.valueOf(Integer.parseInt(pd.get("id").toString())));
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
  
  @RequestMapping({"/back/deleteAllEducation"})
  @org.springframework.web.bind.annotation.ResponseBody
  public Object deleteAllEducation()
  {
	  PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("Education_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){educationService.deleteAllEducations(ArrayNews_IDS);}
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