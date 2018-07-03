package com.health.controller.front;

import com.health.controller.base.BaseController;
import com.health.entity.system.FMenu;
import com.health.entity.system.Page;
import com.health.service.system.FMenuService;
import com.health.service.system.OrderService;
import com.health.system.util.EhcacheUtil;
import com.health.system.util.PageData;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AchevementFController
  extends BaseController
{
  @Resource(name="fmenuService")
  private FMenuService fmenuService;
  @Resource(name="orderService")
  private OrderService orderService;
  
  @RequestMapping(value={"/web/member/toPerformance"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String goPerformance(Page page, ModelMap map) throws Exception
  {
	  String url = "";
	  PageData member = (PageData)SecurityUtils.getSubject().getSession().getAttribute("sessionMember");
	  if (member == null) {
		  url = "front/member/member_login";
	  }else{
		    try
		    {
		      PageData pd = getPageData();
//		      List<FMenu> pfmenulist = getSubMenu(pd.getString("menuId"));
//		      pd.put("submenulist", pfmenulist);
		      pd.put("menuId", 15);
		      pd.put("menuUrl", "web/member/consume.do");
//		      String childMenu = pd.getString("childMenuId");
//		      if(childMenu != null && !childMenu.equals("")){//从主页菜单区点击二级菜单进来
//		    	  pd.put("menuId", childMenu);
//		    	  pd.put("menuUrl", pd.getString("childMenuUrl"));
//		      }else{
//		    	  pd.put("menuId", 15);
//			      pd.put("menuUrl", "web/member/consume.do");
//		    	  pd.put("menuId", pfmenulist.get(0).getMenu_id());
//			      pd.put("menuUrl", pfmenulist.get(0).getMenu_url());
//		      }
		      
		      map.put("fpd", pd);
		      url = "front/achievement/achievement_main";
		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		    }
	  }
	  return url;
  }
  
  /**
   * 消费记录
   * @param page
   * @return
   * @throws Exception
   */
  @RequestMapping({"/web/member/consume"})
  public String toConsume(Page page, ModelMap map) throws Exception
  {
	  PageData member = (PageData)SecurityUtils.getSubject().getSession().getAttribute("sessionMember");
	  if (member == null) {
		  return "front/member/member_login";
	  }else{
		    PageData pd = this.getPageData();
		    
//		    if(pd.get("menuType") != null && pd.get("menuType").toString().equals("1")){//从菜单栏处点击进来的,要先去显示主页面
//		    	pd.put("MENU_ID", pd.get("menuId"));
//		    	PageData menu = fmenuService.getMenuById(pd);
//		    	map.put("menuId", menu.get("PARENT_ID"));
//		    	map.put("childMenuId", menu.get("MENU_ID"));
//		    	map.put("childMenuUrl", menu.get("MENU_URL"));
//		    	return "redirect:/web/member/toPerformance";
//		    }else{//从页面左侧菜单点击进来
		    	Integer memberId = Integer.parseInt(member.get("id").toString());
				pd.put("memberId", memberId);
				pd.put("memberNo", member.get("memberNo"));
				pd.put("status", 4);
				pd.put("hasRefund", 2);
				page.setPd(pd);
			  
			  //查询会员的全部订单
				List<PageData> orderlist = orderService.getDatalistPage(page);
				
				for(PageData item :orderlist){
					String orderId = item.get("id").toString();
					//找到对应的订单商品列表
					List<PageData> productlist = orderService.getDetailist(orderId);
					item.put("productlist", productlist);
				}
				
				map.put("orderlist", orderlist);
				return "front/achievement/member_consume_list";
//		    }
	  }
  }
  
  /**
   * 积分查询
   * @param page
   * @return
   * @throws Exception
   */
  @RequestMapping({"/web/member/score"})
  public String toScore(Page page, ModelMap map) throws Exception
  {
	  PageData member = (PageData)SecurityUtils.getSubject().getSession().getAttribute("sessionMember");
	  if (member == null) {
		  return "front/member/member_login";
	  }else{
		  PageData pd = this.getPageData();
		  if(pd.get("menuType") != null && pd.get("menuType").toString().equals("1")){//从菜单栏处点击进来的,要先去显示主页面
			  pd.put("MENU_ID", pd.get("menuId"));
			  PageData menu = fmenuService.getMenuById(pd);
			  map.put("menuId", menu.get("PARENT_ID"));
			  map.put("childMenuId", menu.get("MENU_ID"));
			  map.put("childMenuUrl", menu.get("MENU_URL"));
			  return "redirect:/web/member/toPerformance";
		  }else{//从页面左侧菜单点击进来
			  map.put("member", member);
			  return "front/achievement/member_score";
		  }
	  }
  }
  
  /**
   * 积分生成明细
   * @param page
   * @return
   * @throws Exception
   */
  @RequestMapping({"/web/member/scoreQuery"})
  public String toScoreQuery(Page page,ModelMap map) throws Exception
  {
	  PageData member = (PageData)SecurityUtils.getSubject().getSession().getAttribute("sessionMember");
	  if (member == null) {
		  return "front/member/member_login";
	  }else{
		  PageData pd = this.getPageData();
		  if(pd.get("menuType") != null && pd.get("menuType").toString().equals("1")){//从菜单栏处点击进来的,要先去显示主页面
			  pd.put("MENU_ID", pd.get("menuId"));
			  PageData menu = fmenuService.getMenuById(pd);
			  map.put("menuId", menu.get("PARENT_ID"));
			  map.put("childMenuId", menu.get("MENU_ID"));
			  map.put("childMenuUrl", menu.get("MENU_URL"));
			  return "redirect:/web/member/toPerformance";
		  }else{//从页面左侧菜单点击进来
			  Integer memberId = Integer.parseInt(member.get("id").toString());
				pd.put("memberId", memberId);
				pd.put("memberNo", member.get("memberNo"));
				pd.put("status", 4);
				pd.put("hasRefund", 2);
				page.setPd(pd);
			  
			  //查询会员的全部订单
				List<PageData> orderlist = orderService.getDatalistPage(page);
				
				for(PageData item :orderlist){
					String orderId = item.get("id").toString();
					//找到对应的订单商品列表
					List<PageData> productlist = orderService.getDetailist(orderId);
					item.put("productlist", productlist);
				}
				
				map.put("orderlist", orderlist);
			  return "front/achievement/member_score_query";
		  }
	  }
  }
  
  /**
   * 实时业绩查询
   * @param page
   * @return
   * @throws Exception
   */
  @RequestMapping({"/web/member/realTimeAchievement"})
  public String toRealTimeAchievement(Page page, ModelMap map) throws Exception
  {
	  PageData pd = this.getPageData();
	  if(pd.get("menuType") != null && pd.get("menuType").toString().equals("1")){//从菜单栏处点击进来的,要先去显示主页面
	    	pd.put("MENU_ID", pd.get("menuId"));
	    	PageData menu = fmenuService.getMenuById(pd);
	    	map.put("menuId", menu.get("PARENT_ID"));
	    	map.put("childMenuId", menu.get("MENU_ID"));
	    	map.put("childMenuUrl", menu.get("MENU_URL"));
	    	return "redirect:/web/member/toPerformance";
	    }else{//从页面左侧菜单点击进来
	    	
	    	return "front/achievement/member_realtime_achievement";
	    }
  }
  
  private List<FMenu> getSubMenu(String menuId)
  {
    List<FMenu> pfmenulist = new ArrayList();
    if (EhcacheUtil.getInstance().get("ehcacheGO", "MENU_ACHIEVEMENT_SUBMENU") != null)
    {
      Object submenulist = EhcacheUtil.getInstance().get("ehcacheGO", "MENU_ACHIEVEMENT_SUBMENU");
      pfmenulist = (List)submenulist;
    }
    else
    {
      Integer menuid = Integer.valueOf(Integer.parseInt(menuId));
      try
      {
        FMenu menu = this.fmenuService.getMenuById(menuid);
        Integer parentId = menu.getParent_id().intValue() == 0 ? menu.getMenu_id() : menu.getParent_id();
        pfmenulist = this.fmenuService.listSubMenuByParentId(parentId);
        EhcacheUtil.getInstance().put("ehcacheGO", "MENU_ACHIEVEMENT_SUBMENU", pfmenulist);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return pfmenulist;
  }
}
