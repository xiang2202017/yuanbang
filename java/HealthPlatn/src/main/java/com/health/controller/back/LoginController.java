package com.health.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Menu;
import com.health.entity.system.Role;
import com.health.entity.system.User;
import com.health.service.system.MenuService;
import com.health.service.system.RoleService;
import com.health.service.system.UserService;
import com.health.system.interceptor.shiro.CustomizedToken;
import com.health.system.util.AppUtil;
import com.health.system.util.DateUtil;
import com.health.system.util.PageData;
import com.health.system.util.RightsHelper;
import com.health.system.util.Tools;
import com.health.system.util.VerifyCodeUtils;

@Controller
public class LoginController
  extends BaseController
{
  @Resource(name="userService")
  private UserService userService;
  @Resource(name="menuService")
  private MenuService menuService;
  @Resource(name="roleService")
  private RoleService roleService;
  
  public void getRemortIP(String USERNAME)
    throws Exception
  {
    PageData pd = new PageData();
    HttpServletRequest request = getRequest();
    String ip = "";
    if (request.getHeader("x-forwarded-for") == null) {
      ip = request.getRemoteAddr();
    } else {
      ip = request.getHeader("x-forwarded-for");
    }
    pd.put("USERNAME", USERNAME);
    pd.put("IP", ip);
    this.userService.saveIP(pd);
  }
  
  @RequestMapping({"/system"})
  public ModelAndView toLogin()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    
    String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
    
    HttpSession session = getRequest().getSession(true);
    session.setAttribute("rand", verifyCode.toLowerCase());
    
    int w = 200;int h = 80;
    
    PageData pd = getPageData();
    pd.put("SYSNAME", Tools.readTxtFile("admin/config/SYSNAME.txt"));
    
    mv.setViewName("back/login");
    mv.addObject("pd", pd);
    return mv;
  }
  
  @RequestMapping(value={"/login_login"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public Object login()
    throws Exception
  {
    Map<String, String> map = new HashMap();
    PageData pd = new PageData();
    pd = getPageData();
    String errInfo = "";
    String[] KEYDATA = pd.getString("KEYDATA").replaceAll("33032088", "").replaceAll("33032089", "").split(",ab,");
    if ((KEYDATA != null) && (KEYDATA.length == 3))
    {
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      String sessionCode = "123";
      
      String code = KEYDATA[2];
      if ((code == null) || ("".equals(code)))
      {
        errInfo = "nullcode";
      }
      else
      {
        String USERNAME = KEYDATA[0];
        String PASSWORD = KEYDATA[1];
        pd.put("USERNAME", USERNAME);
        if ((Tools.notEmpty(sessionCode)) && (sessionCode.equalsIgnoreCase(code)))
        {
          String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();
          pd.put("PASSWORD", passwd);
          pd = this.userService.getUserByNameAndPwd(pd);
          if (pd != null)
          {
            pd.put("LAST_LOGIN", DateUtil.getTime().toString());
            this.userService.updateLastLogin(pd);
            User user = new User();
            user.setUSER_ID(pd.getString("USER_ID"));
            user.setUSERNAME(pd.getString("USERNAME"));
            user.setPASSWORD(pd.getString("PASSWORD"));
            user.setNAME(pd.getString("NAME"));
            user.setRIGHTS(pd.getString("RIGHTS"));
            user.setROLE_ID(pd.getString("ROLE_ID"));
            user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
            user.setIP(pd.getString("IP"));
            user.setSTATUS(pd.getString("STATUS"));
            session.setAttribute("sessionUser", user);
            session.removeAttribute("sessionSecCode");
            
            Subject subject = SecurityUtils.getSubject();
            
            CustomizedToken token = new CustomizedToken(USERNAME, PASSWORD, "Admin");
            try
            {
              subject.login(token);
            }
            catch (AuthenticationException e)
            {
              errInfo = "身份验证失败！";
            }
          }
          else
          {
            errInfo = "usererror";
          }
        }
        else
        {
          errInfo = "codeerror";
        }
        if (Tools.isEmpty(errInfo)) {
          errInfo = "success";
        }
      }
    }
    else
    {
      errInfo = "error";
    }
    map.put("result", errInfo);
    return AppUtil.returnObject(new PageData(), map);
  }
  
  @RequestMapping({"/main/{changeMenu}"})
  public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu)
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    try
    {
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      
      User user = (User)session.getAttribute("sessionUser");
      if (user != null)
      {
        User userr = (User)session.getAttribute("USERROL");
        if (userr == null)
        {
          user = this.userService.getUserAndRoleById(user.getUSER_ID());
          session.setAttribute("USERROL", user);
        }
        else
        {
          user = userr;
        }
        Role role = user.getRole();
        String roleRights = role != null ? role.getRIGHTS() : "";
        
        session.setAttribute("sessionRoleRights", roleRights);
        session.setAttribute("USERNAME", user.getUSERNAME());
        
        List<Menu> allmenuList = new ArrayList();
        if (session.getAttribute("allmenuList") == null)
        {
          allmenuList = this.menuService.listAllMenu();
          if (Tools.notEmpty(roleRights)) {
            for (Menu menu : allmenuList)
            {
              menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
              if (menu.isHasMenu())
              {
                List<Menu> subMenuList = menu.getSubMenu();
                for (Menu sub : subMenuList) {
                  sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
                }
              }
            }
          }
          session.setAttribute("allmenuList", allmenuList);
        }
        else
        {
          allmenuList = (List)session.getAttribute("allmenuList");
        }
        List<Menu> menuList = new ArrayList();
        if ((session.getAttribute("menuList") == null) || ("yes".equals(changeMenu)))
        {
          Object menuList1 = new ArrayList();
          List<Menu> menuList2 = new ArrayList();
          for (int i = 0; i < allmenuList.size(); i++)
          {
            Menu menu = (Menu)allmenuList.get(i);
            if ("1".equals(menu.getMENU_TYPE())) {
              ((List)menuList1).add(menu);
            } else {
              menuList2.add(menu);
            }
          }
          session.removeAttribute("menuList");
          if ("2".equals(session.getAttribute("changeMenu")))
          {
            session.setAttribute("menuList", menuList1);
            session.removeAttribute("changeMenu");
            session.setAttribute("changeMenu", "1");
            menuList = (List<Menu>)menuList1;
          }
          else
          {
            session.setAttribute("menuList", menuList2);
            session.removeAttribute("changeMenu");
            session.setAttribute("changeMenu", "2");
            menuList = menuList2;
          }
        }
        else
        {
          menuList = (List)session.getAttribute("menuList");
        }
        if (session.getAttribute("QX") == null) {
          session.setAttribute("QX", getUQX(session));
        }
        String strXML = "<graph caption='��12������������������' xAxisName='����' yAxisName='��' decimalPrecision='0' formatNumberScale='0'><set name='2013-05' value='4' color='AFD8F8'/><set name='2013-04' value='0' color='AFD8F8'/><set name='2013-03' value='0' color='AFD8F8'/><set name='2013-02' value='0' color='AFD8F8'/><set name='2013-01' value='0' color='AFD8F8'/><set name='2012-01' value='0' color='AFD8F8'/><set name='2012-11' value='0' color='AFD8F8'/><set name='2012-10' value='0' color='AFD8F8'/><set name='2012-09' value='0' color='AFD8F8'/><set name='2012-08' value='0' color='AFD8F8'/><set name='2012-07' value='0' color='AFD8F8'/><set name='2012-06' value='0' color='AFD8F8'/></graph>";
        mv.addObject("strXML", strXML);
        
        mv.setViewName("back/index");
        mv.addObject("user", user);
        mv.addObject("menuList", menuList);
      }
      else
      {
        mv.setViewName("back/login");
      }
    }
    catch (Exception e)
    {
      mv.setViewName("back/login");
      this.logger.error(e.getMessage(), e);
    }
    pd.put("SYSNAME", Tools.readTxtFile("admin/config/SYSNAME.txt"));
    mv.addObject("pd", pd);
    return mv;
  }
  
  @RequestMapping({"/tab"})
  public String tab()
  {
    return "back/tab";
  }
  
  @RequestMapping({"/login_default"})
  public String defaultPage()
  {
    return "back/default";
  }
  
  @RequestMapping({"/logout"})
  public ModelAndView logout()
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    
    session.removeAttribute("sessionUser");
    session.removeAttribute("sessionRoleRights");
    session.removeAttribute("allmenuList");
    session.removeAttribute("menuList");
    session.removeAttribute("QX");
    session.removeAttribute("userpds");
    session.removeAttribute("USERNAME");
    session.removeAttribute("USERROL");
    session.removeAttribute("changeMenu");
    
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    
    pd = getPageData();
    String msg = pd.getString("msg");
    pd.put("msg", msg);
    
    pd.put("SYSNAME", Tools.readTxtFile("admin/config/SYSNAME.txt"));
    mv.setViewName("back/login");
    mv.addObject("pd", pd);
    return mv;
  }
  
  public Map<String, String> getUQX(Session session)
  {
    PageData pd = new PageData();
    Map<String, String> map = new HashMap();
    try
    {
      String USERNAME = session.getAttribute("USERNAME").toString();
      pd.put("USERNAME", USERNAME);
      String ROLE_ID = this.userService.findByUId(pd).get("ROLE_ID").toString();
      
      pd.put("ROLE_ID", ROLE_ID);
      
      PageData pd2 = new PageData();
      pd2.put("USERNAME", USERNAME);
      pd2.put("ROLE_ID", ROLE_ID);
      
      pd = this.roleService.findObjectById(pd);
      
      pd2 = this.roleService.findGLbyrid(pd2);
      if (pd2 != null)
      {
        map.put("FX_QX", pd2.get("FX_QX").toString());
        map.put("FW_QX", pd2.get("FW_QX").toString());
        map.put("QX1", pd2.get("QX1").toString());
        map.put("QX2", pd2.get("QX2").toString());
        map.put("QX3", pd2.get("QX3").toString());
        map.put("QX4", pd2.get("QX4").toString());
        
        pd2.put("ROLE_ID", ROLE_ID);
        pd2 = this.roleService.findYHbyrid(pd2);
        map.put("C1", pd2.get("C1").toString());
        map.put("C2", pd2.get("C2").toString());
        map.put("C3", pd2.get("C3").toString());
        map.put("C4", pd2.get("C4").toString());
        map.put("Q1", pd2.get("Q1").toString());
        map.put("Q2", pd2.get("Q2").toString());
        map.put("Q3", pd2.get("Q3").toString());
        map.put("Q4", pd2.get("Q4").toString());
      }
      map.put("adds", pd.getString("ADD_QX"));
      map.put("dels", pd.getString("DEL_QX"));
      map.put("edits", pd.getString("EDIT_QX"));
      map.put("chas", pd.getString("CHA_QX"));
      
      getRemortIP(USERNAME);
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return map;
  }
}