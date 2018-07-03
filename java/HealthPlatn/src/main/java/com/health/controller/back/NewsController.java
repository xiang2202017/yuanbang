package com.health.controller.back;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.entity.system.User;
import com.health.service.system.NewsService;
import com.health.system.util.AppUtil;
import com.health.system.util.Const;
import com.health.system.util.CustomUtil;
import com.health.system.util.Jurisdiction;
import com.health.system.util.PageData;
import com.health.system.util.StringUtil;

@Controller
public class NewsController
  extends BaseController
{
  String menuUrl = "toNewsList.do";
  @Resource(name="newsService")
  private NewsService newsService;
  
  @RequestMapping({"/back/toNewsList"})
  public ModelAndView toList(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pg = new PageData();
    
    page.setPd(pg);
    List<PageData> newsList = this.newsService.getNewsList(page);
    mv.setViewName("back/news/news_list");
    mv.addObject("newsList", newsList);
    mv.addObject("pg", pg);
    
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/listNews"})
  public ModelAndView listNews(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String title = pd.getString("title");
    String keywords = pd.getString("keywords");
    String author = pd.getString("author");
    String type = pd.getString("type");
    if ((type != null) && (!"".equals(type)) && ("0".equals(type))) {
      pd.put("type", "");
    }
    if ((keywords != null) && (!"".equals(keywords)))
    {
      keywords = keywords.trim();
      pd.put("keywords", keywords);
    }
    if ((author != null) && (!"".equals(author)))
    {
      author = author.trim();
      pd.put("author", author);
    }
    if ((title != null) && (!"".equals(title)))
    {
      title = title.trim();
      pd.put("title", title);
    }
    String fromTime = pd.getString("fromTime");
    String toTime = pd.getString("toTime");
    if ((fromTime != null) && (!"".equals(fromTime)))
    {
      fromTime = fromTime + " 00:00:00";
      pd.put("fromTime", fromTime);
    }
    if ((toTime != null) && (!"".equals(toTime)))
    {
      toTime = toTime + " 00:00:00";
      pd.put("toTime", toTime);
    }
    page.setPd(pd);
    List<PageData> newsList = this.newsService.getDatalistPage(page);
    
    mv.setViewName("back/news/news_list");
    mv.addObject("newsList", newsList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/toNewsView"})
  public ModelAndView toNewsView()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String id = getRequest().getParameter("id");
    pd = this.newsService.getNewsDetailById(id);
    
    String ctime = pd.getString("creatime");
    String timestr = (ctime.equals("")) || (ctime == null) ? pd.getString("editime") : ctime;
    String imgpath = pd.getString("imgPath");
    String headstr = CustomUtil.getNewsHeader(pd.getString("title"), imgpath, timestr, pd.get("clickNum").toString());
    pd.put("content", headstr + pd.getString("content"));
    mv.addObject("pd", pd);
    mv.setViewName("back/news/news_view");
    
    return mv;
  }
  
  @RequestMapping({"/back/toNewsAdd"})
  public ModelAndView toAdd()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      mv.setViewName("back/news/news_add");
      mv.addObject("pg", pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping(value={"/back/uploadMainImg"}, produces={"text/html;chartset=utf-8"})
  @ResponseBody
  public Object uploadNewsMainImg(HttpServletRequest request, @RequestParam("uploadimg") CommonsMultipartFile uploadimg)
    throws IOException
  {
    String realPath = getRequest().getSession().getServletContext().getRealPath("/");
    String saveDirectoryPath = realPath + "\\uploadFiles\\uploadImgs\\temp\\";
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    MultipartFile mulfile = multipartRequest.getFile("uploadimg");
    
    String filename = null;
    if (uploadimg.isEmpty()) {
      return "{'msg':'fail', 'reason':'文件不能为空'}";
    }
    filename = System.currentTimeMillis() + uploadimg.getOriginalFilename();
    try
    {
      FileUtils.copyInputStreamToFile(uploadimg.getInputStream(), new File(saveDirectoryPath, filename));
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return "{'msg':'fail', 'reason':'文件上传失败'}";
    }
    String path = "/HealthPlatn/uploadFiles/uploadImgs/temp/" + filename;
    return "{'msg':'success','path':'" + path + "'}";
  }
  
  @RequestMapping({"/back/addNews"})
  public String addNews()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      this.logger.debug(pg);
      
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      User user = (User)session.getAttribute("sessionUser");
      
      pg.put("creator", user.getNAME());
      pg.put("author", (pg.get("author") == null) || (pg.get("author").equals("")) ? user.getNAME() : pg.getString("author"));
      
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      pg.put("creatime", df.format(new Date()));
      pg.put("clickNum", Integer.valueOf(0));
      pg.put("content", pg.getString("editorValue"));
      pg.put("imgPath", pg.getString("mainImgPath"));
      Object remarko = pg.get("contentTxt");
      if ((remarko != null) && (!remarko.equals(""))) {
        pg.put("remark", StringUtil.getStrScope(pg.getString("contentTxt"), Const.STR_LENGTH.intValue()));
      } else {
        pg.put("remark", "暂无详情");
      }
      this.newsService.insertNews(pg);
      
      pg.put("news_id", pg.get("id"));
      this.newsService.insertNewsDetail(pg);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listNews";
  }
  
  @RequestMapping({"/back/toNewsEdit"})
  public ModelAndView toEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      mv.setViewName("back/news/news_edit");
      pd = getPageData();
      pd = this.newsService.getNewsDetailById(pd.getString("id"));
      String discription = pd.getString("content").replace("\"", "'");
      pd.put("content", discription);
      mv.addObject("pd", pd);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateNews"})
  public String newsEdit()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      User user = (User)session.getAttribute("sessionUser");
      
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      pg.put("editime", df.format(new Date()));
      pg.put("editor", user.getNAME());
      pg.put("imgPath", pg.getString("mainImgPath"));
      pg.put("content", pg.getString("editorValue"));
      Object remarko = pg.get("contentTxt");
      if ((remarko != null) && (!remarko.equals(""))) {
        pg.put("remark", StringUtil.getStrScope(pg.getString("contentTxt"), Const.STR_LENGTH.intValue()));
      } else {
        pg.put("remark", "暂无详情");
      }
      this.newsService.updateNews(pg);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listNews";
  }
  
  @RequestMapping({"/back/deleteNews"})
  public void deleteU(PrintWriter out)
  {
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (Jurisdiction.buttonJurisdiction(this.menuUrl, "dels")) {
        this.newsService.deleteNewsById(Integer.valueOf(Integer.parseInt(pd.get("id").toString())));
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
	 * 批量删除资讯
	 */
	@RequestMapping(value="/back/deleteAllNews")
	@ResponseBody
	public Object deleteAllU() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("News_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){newsService.deleteAllNews(ArrayNews_IDS);}
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
