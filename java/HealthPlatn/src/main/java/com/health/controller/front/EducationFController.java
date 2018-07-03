package com.health.controller.front;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.health.controller.base.BaseController;
import com.health.service.system.EducationService;
import com.health.system.util.CustomUtil;
import com.health.system.util.PageData;

@Controller
public class EducationFController
  extends BaseController
{
  @Resource(name="educationService")
  private EducationService educationService;
  
  @RequestMapping(value={"/web/toEducation"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String toEducation(ModelMap map)
    throws Exception
  {
    String content = "";
    PageData education = null;
    List<PageData> typeList = this.educationService.getEducationTypeList();
    int i = 0;
    for (PageData pd : typeList)
    {
      List<PageData> elist = this.educationService.getEducationByTypeId(pd.get("id").toString());
      if ((i == 0) && (elist != null) && (elist.size() > 0))
      {
        education = this.educationService.getEducationDetailById(((PageData)elist.get(0)).get("id").toString());
        content = CustomUtil.getEducationHeader(education.getString("name")) + education.getString("content");
      }
      pd.put("sub", elist);
      i++;
    }
    map.put("typeList", typeList);
    map.put("content", content);
    map.put("education", education);
    return "front/education/education_main";
  }
  
  @RequestMapping(value={"/web/toEducationView"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String toEducationView(ModelMap mv)
    throws Exception
  {
    PageData pd = getPageData();
    
    String id = getRequest().getParameter("educationId");
    pd = this.educationService.getEducationDetailById(id);
    String headstr = CustomUtil.getEducationHeader(pd.getString("name"));
    mv.addAttribute("education", pd);
    mv.addAttribute("content", headstr + pd.getString("content"));
    
    return "front/education/education_view";
  }
}
