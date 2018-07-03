package com.health.controller.back;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.entity.system.Role;
import com.health.entity.system.User;
import com.health.service.system.MenuService;
import com.health.service.system.RoleService;
import com.health.service.system.UserService;
import com.health.system.util.AppUtil;
import com.health.system.util.Const;
import com.health.system.util.Jurisdiction;
import com.health.system.util.Logger;
import com.health.system.util.PageData;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController
  extends BaseController
{
  String menuUrl = "back/listUsers.do";
  @Resource(name="userService")
  private UserService userService;
  @Resource(name="roleService")
  private RoleService roleService;
  @Resource(name="menuService")
  private MenuService menuService;
  
  @RequestMapping({"/back/saveU"})
  public ModelAndView saveU(PrintWriter out)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    pd.put("USER_ID", get32UUID());
    pd.put("RIGHTS", "");
    pd.put("LAST_LOGIN", "");
    pd.put("IP", "");
    pd.put("STATUS", "0");
    pd.put("SKIN", "default");
    
    pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
    if (this.userService.findByUId(pd) == null)
    {
      if (Jurisdiction.buttonJurisdiction(this.menuUrl, "add")) {
        this.userService.saveU(pd);
      }
      mv.addObject("msg", "success");
    }
    else
    {
      mv.addObject("msg", "failed");
    }
    mv.setViewName("back/save_result");
    return mv;
  }
  
  @RequestMapping({"/back/getUname"})
  @ResponseBody
  public Object getList()
  {
	  PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			
			if(null == pds){
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
				pd.put("USERNAME", USERNAME);
				pds = userService.findByUId(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}
			
			pdList.add(pds);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
  }
  
  @RequestMapping({"/back/hasU"})
  @ResponseBody
  public Object hasU()
  {
    Map<String, String> map = new HashMap();
    String errInfo = "success";
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (this.userService.findByUId(pd) != null) {
        errInfo = "error";
      }
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    map.put("result", errInfo);
    return AppUtil.returnObject(new PageData(), map);
  }
  
  @RequestMapping({"/back/hasE"})
  @ResponseBody
  public Object hasE()
  {
    Map<String, String> map = new HashMap();
    String errInfo = "success";
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (this.userService.findByUE(pd) != null) {
        errInfo = "error";
      }
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    map.put("result", errInfo);
    return AppUtil.returnObject(new PageData(), map);
  }
  
  @RequestMapping({"/back/hasN"})
  @ResponseBody
  public Object hasN()
  {
    Map<String, String> map = new HashMap();
    String errInfo = "success";
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (this.userService.findByUN(pd) != null) {
        errInfo = "error";
      }
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    map.put("result", errInfo);
    return AppUtil.returnObject(new PageData(), map);
  }
  
  @RequestMapping({"/back/editU"})
  public ModelAndView editU()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    if ((pd.getString("PASSWORD") != null) && (!"".equals(pd.getString("PASSWORD")))) {
      pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
    }
    if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) {
      this.userService.editU(pd);
    }
    mv.addObject("msg", "success");
    mv.setViewName("back/save_result");
    return mv;
  }
  
  @RequestMapping({"/back/goEditU"})
  public ModelAndView goEditU()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String fx = pd.getString("fx");
    if ("head".equals(fx)) {
      mv.addObject("fx", "head");
    } else {
      mv.addObject("fx", "user");
    }
    List<Role> roleList = this.roleService.listAllERRoles();
    pd = this.userService.findByUiId(pd);
    mv.setViewName("back/user/user_edit");
    mv.addObject("msg", "editU");
    mv.addObject("pd", pd);
    mv.addObject("roleList", roleList);
    
    return mv;
  }
  
  @RequestMapping({"/back/goAddU"})
  public ModelAndView goAddU()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    List<Role> roleList = this.roleService.listAllERRoles();
    
    mv.setViewName("back/user/user_edit");
    mv.addObject("msg", "saveU");
    mv.addObject("pd", pd);
    mv.addObject("roleList", roleList);
    
    return mv;
  }
  
  @RequestMapping({"/back/listUsers"})
  public ModelAndView listUsers(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String USERNAME = pd.getString("USERNAME");
    if ((USERNAME != null) && (!"".equals(USERNAME)))
    {
      USERNAME = USERNAME.trim();
      pd.put("USERNAME", USERNAME);
    }
    String lastLoginStart = pd.getString("lastLoginStart");
    String lastLoginEnd = pd.getString("lastLoginEnd");
    if ((lastLoginStart != null) && (!"".equals(lastLoginStart)))
    {
      lastLoginStart = lastLoginStart + " 00:00:00";
      pd.put("lastLoginStart", lastLoginStart);
    }
    if ((lastLoginEnd != null) && (!"".equals(lastLoginEnd)))
    {
      lastLoginEnd = lastLoginEnd + " 00:00:00";
      pd.put("lastLoginEnd", lastLoginEnd);
    }
    page.setPd(pd);
    List<PageData> userList = this.userService.listPdPageUser(page);
    List<Role> roleList = this.roleService.listAllERRoles();
    
    mv.setViewName("back/user/user_list");
    mv.addObject("userList", userList);
    mv.addObject("roleList", roleList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/deleteU"})
  public void deleteU(PrintWriter out)
  {
    PageData pd = new PageData();
    try
    {
      pd = getPageData();
      if (Jurisdiction.buttonJurisdiction(this.menuUrl, "dels")) {
        this.userService.deleteU(pd);
      }
      out.write("success");
      out.close();
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
  }
  
  @RequestMapping({"/back/deleteAllU"})
  @ResponseBody
  public Object deleteAllU()
  {
	  PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("USER_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){userService.deleteAllU(ArrayNews_IDS);}
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
  
  @InitBinder
  public void initBinder(WebDataBinder binder)
  {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
  }
  
  public Map<String, String> getHC()
  {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    return (Map)session.getAttribute("QX");
  }
}
