package com.health.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.FMenu;
import com.health.entity.system.News;
import com.health.entity.system.Page;
import com.health.service.system.FMenuService;
import com.health.service.system.NewsService;
import com.health.service.system.ProductService;
import com.health.system.util.Const;
import com.health.system.util.CustomUtil;
import com.health.system.util.EhcacheUtil;
import com.health.system.util.PageData;

@Controller
public class FMenuController
  extends BaseController
{
  @Resource(name="fmenuService")
  private FMenuService fmenuService;
  @Resource(name="newsService")
  private NewsService newsService;
  @Resource(name="productService")
  private ProductService productService;
  
  @RequestMapping({"/home"})
  public ModelAndView gohome()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      String intype = pd.getString("menuType");
      
      List<News> comNews = this.newsService.getNewsForLen(Integer.valueOf(0), Integer.valueOf(5), Const.NEWS_TYPE_COMPONY);
      List<News> healthNews = this.newsService.getNewsForLen(Integer.valueOf(0), Integer.valueOf(8), Const.NEWS_TYPE_HEALTH);
      pd.put("comNews", comNews);
      pd.put("healthNews", healthNews);
      
      @SuppressWarnings("unchecked")
	List<FMenu> fmenulist = (List<FMenu>)getRequest().getSession().getAttribute("frontMenulist");
      if ((fmenulist == null) || (intype == null) || (intype.length() == 0))
      {
        fmenulist = this.fmenuService.listAllMenu();
        for (FMenu menu : fmenulist) {
          if (menu.getMenu_id() == Const.MENU_PRODUCT)
          {
            List<PageData> typeList = this.productService.getProductTypeList();
            List<FMenu> sublist = new ArrayList<FMenu>();
            for (PageData type : typeList)
            {
              FMenu submenu = convertTypeToMenu(type, menu.getMenu_url(), menu.getMenu_id());
              sublist.add(submenu);
            }
            menu.setSubMenu(sublist);
            break;
          }
        }
        getRequest().getSession().setAttribute("frontMenulist", fmenulist);
        
        pd.put("pagePath", "home.jsp");
        mv.setViewName("front/index");
      }
      else
      {
        mv.setViewName("front/home");
      }
      mv.addObject("fpd", pd);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return mv;
  }
  
  @RequestMapping({"/web/newsList"})
  public ModelAndView goNewsList(Page page)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      List<FMenu> pfmenulist = new ArrayList<FMenu>();
      
      String id = getRequest().getParameter("newsId");
      Integer type;
      if ((id != null) && (!id.equals("")))
      {
        PageData news = this.newsService.getNewsById(id);
        type = Integer.valueOf(Integer.parseInt(news.get("type").toString()));
      }
      else
      {
        type = Integer.valueOf(pd.getString("type") == null ? Const.NEWS_TYPE_COMPONY.intValue() : Integer.parseInt(pd.getString("type")));
      }
      pfmenulist = getNewsSubMenu("2");
      
      if(type == 1){
    	  pd.put("menuId", Const.NEWS_MENU_COMPONY); 
      }else if(type == 2){
    	  pd.put("menuId", Const.NEWS_MENU_HEALTH); 
      }else if(type == 3){
    	  pd.put("menuId", Const.NEWS_MENU_EDUCATION); 
      }else if(type == 4){
    	  pd.put("menuId", Const.NEWS_MENU_TRAVEL); 
      }
      pd.put("type", type);
      
      page.setPd(pd);
      List<PageData> comNews = this.newsService.getDatalistPage(page);
      pd.put("newslist", comNews);
      
      List<News> topNews = this.newsService.getNewsForTop(type);
      pd.put("topNews", topNews);
      
      pd.put("submenulist", pfmenulist);
      
      String title = type == Const.NEWS_TYPE_COMPONY ? "公司资讯" : "健康资讯";
      mv.setViewName("front/news/news_main");
      mv.addObject("fpd", pd);
      mv.addObject("newsId", id);
      mv.addObject("title", title);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return mv;
  }
  
  @RequestMapping(value={"/web/compNews"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String goCompNews(Page page, ModelMap model)
    throws Exception
  {
    String path = "";
    try
    {
      PageData pd = new PageData();
      pd.put("type", Const.NEWS_TYPE_COMPONY);
      page.setPd(pd);
      
      List<FMenu> pfmenulist = getNewsSubMenu(pd.getString("menuId"));
      pd.put("submenulist", pfmenulist);
      pd.put("menuId", Const.NEWS_MENU_COMPONY);
      path = "front/news/news_main";
      
      List<PageData> comNews = this.newsService.getDatalistPage(page);
      pd.put("newslist", comNews);
      List<News> topNews = this.newsService.getNewsForTop(Const.NEWS_TYPE_COMPONY);
      pd.put("topNews", topNews);
      model.addAttribute("fpd", pd);
      model.addAttribute("title", "公司资讯");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return path;
  }
  
  @RequestMapping(value={"/web/healthNews"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String goHealthNews(Page page, ModelMap model)
    throws Exception
  {
    String path = "";
    try
    {
      PageData pd = new PageData();
      pd.put("type", Const.NEWS_TYPE_HEALTH);
      pd.put("menuId", Const.NEWS_MENU_HEALTH);
      page.setPd(pd);
      
      String menuId = getRequest().getParameter("menuId");
      List<FMenu> pfmenulist = getNewsSubMenu(menuId);
      pd.put("submenulist", pfmenulist);
      path = "front/news/news_main";
      
      List<PageData> comNews = this.newsService.getDatalistPage(page);
      pd.put("newslist", comNews);
      List<News> topNews = this.newsService.getNewsForTop(Const.NEWS_TYPE_HEALTH);
      pd.put("topNews", topNews);
      model.addAttribute("fpd", pd);
      model.addAttribute("title", "健康资讯");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return path;
  }
  
  @RequestMapping(value={"/web/educationNews"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String goducationNews(Page page, ModelMap model)
    throws Exception
  {
    String path = "";
    try
    {
      PageData pd = this.getPageData();
      pd.put("type", Const.NEWS_TYPE_EDUCATION);
      pd.put("menuId", Const.NEWS_MENU_EDUCATION);
      page.setPd(pd);
      
      List<FMenu> pfmenulist = getNewsSubMenu(this.getRequest().getParameter("menuId"));
      pd.put("submenulist", pfmenulist);
      path = "front/news/news_main";
      
      List<PageData> comNews = this.newsService.getDatalistPage(page);
      pd.put("newslist", comNews);
      List<News> topNews = this.newsService.getNewsForTop(Const.NEWS_TYPE_EDUCATION);
      pd.put("topNews", topNews);
      model.addAttribute("fpd", pd);
      model.addAttribute("title", "教育资讯");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return path;
  }
  
  @RequestMapping(value={"/web/travelNews"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String goravelNews(Page page, ModelMap model)
    throws Exception
  {
    String path = "";
    try
    {
      PageData pd = new PageData();
      pd.put("type", Const.NEWS_TYPE_TRAVEL);
      pd.put("menuId", Const.NEWS_MENU_TRAVEL);
      page.setPd(pd);
      
      List<FMenu> pfmenulist = getNewsSubMenu(this.getRequest().getParameter("menuId"));
      pd.put("submenulist", pfmenulist);
      path = "front/news/news_main";
      
      List<PageData> comNews = this.newsService.getDatalistPage(page);
      pd.put("newslist", comNews);
      List<News> topNews = this.newsService.getNewsForTop(Const.NEWS_TYPE_TRAVEL);
      pd.put("topNews", topNews);
      model.addAttribute("fpd", pd);
      model.addAttribute("title", "旅游资讯");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return path;
  }
  
  @RequestMapping(value={"/web/newsDetail"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String getNewsDetail(ModelMap model)
    throws Exception
  {
    PageData pd = new PageData();
    String id = getRequest().getParameter("newsId");
    pd = this.newsService.getNewsDetailById(id);
    
    PageData nextPd = this.newsService.getNewsDetailByNextId(pd);
    PageData prePd = this.newsService.getNewsDetailByPreId(pd);
    
    Integer clickNum = Integer.valueOf(Integer.parseInt(pd.get("clickNum").toString()) + 1);
    pd.put("clickNum", clickNum);
    this.newsService.updateNewsClickNum(pd);
    
    String ctime = pd.getString("creatime");
    String timestr = (ctime.equals("")) || (ctime == null) ? pd.getString("editime") : ctime;
    String imgpath = pd.getString("imgPath");
    String headstr = CustomUtil.getNewsHeader(pd.getString("title"), imgpath, timestr, pd.get("clickNum").toString());
    model.addAttribute("content", headstr + pd.getString("content"));
    model.addAttribute("previousNews", prePd);
    model.addAttribute("nextNews", nextPd);
    return "front/news/news_view";
  }
  
  @RequestMapping(value={"/web/contactUs"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String toContactUs(ModelMap model)
    throws Exception
  {
    return "front/contact";
  }
  
  private FMenu convertTypeToMenu(PageData pd, String menurl, Integer menuId)
  {
    FMenu menu = new FMenu();
    menu.setParent_id(menuId);
    menu.setMenu_name(pd.getString("name"));
    menu.setMenu_url(menurl + "?type=" + pd.get("id").toString());
    return menu;
  }
  
  @SuppressWarnings("unchecked")
private List<FMenu> getNewsSubMenu(String menuId)
  {
    List<FMenu> pfmenulist = new ArrayList<FMenu>();
    if (EhcacheUtil.getInstance().get("ehcacheGO", "MENU_NEWS_SUBMENU") != null)
    {
      Object submenulist = EhcacheUtil.getInstance().get("ehcacheGO", "MENU_NEWS_SUBMENU");
      pfmenulist = (List<FMenu>)submenulist;
    }
    else
    {
      Integer menuid = Integer.valueOf(Integer.parseInt(menuId));
      try
      {
        FMenu menu = this.fmenuService.getMenuById(menuid);
        Integer parentId = menu.getParent_id().intValue() == 0 ? menu.getMenu_id() : menu.getParent_id();
        pfmenulist = this.fmenuService.listSubMenuByParentId(parentId);
        EhcacheUtil.getInstance().put("ehcacheGO", "MENU_NEWS_SUBMENU", pfmenulist);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return pfmenulist;
  }
}