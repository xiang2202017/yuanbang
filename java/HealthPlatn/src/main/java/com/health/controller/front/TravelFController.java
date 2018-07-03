package com.health.controller.front;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.health.controller.base.BaseController;
import com.health.service.system.TravelService;
import com.health.system.util.CustomUtil;
import com.health.system.util.PageData;

@Controller
public class TravelFController
  extends BaseController
{
  @Resource(name="travelService")
  private TravelService travelService;
  
  @RequestMapping(value={"/web/toTravel"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String toTravel(ModelMap map)
    throws Exception
  {
    String content = "";
    PageData travel = null;
    List<PageData> typeList = this.travelService.getTravelTypeList();
    int i = 0;
    for (PageData pd : typeList)
    {
      List<PageData> elist = this.travelService.getTravelByTypeId(pd.get("id").toString());
      if ((i == 0) && (elist != null) && (elist.size() > 0))
      {
        travel = this.travelService.getTravelDetailById(((PageData)elist.get(0)).get("id").toString());
        content = CustomUtil.getTravelHeader(travel.getString("name")) + travel.getString("content");
      }
      pd.put("sub", elist);
      i++;
    }
    map.put("typeList", typeList);
    map.put("content", content);
    map.put("travel", travel);
    return "front/travel/travel_main";
  }
  
  @RequestMapping(value={"/web/toTravelView"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String toTravelView(ModelMap mv)
    throws Exception
  {
    PageData pd = getPageData();
    
    String id = getRequest().getParameter("travelId");
    pd = this.travelService.getTravelDetailById(id);
    String headstr = CustomUtil.getTravelHeader(pd.getString("name"));
    mv.addAttribute("travel", pd);
    mv.addAttribute("content", headstr + pd.getString("content"));
    
    return "front/travel/travel_view";
  }
}
