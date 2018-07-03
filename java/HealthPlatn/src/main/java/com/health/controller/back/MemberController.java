package com.health.controller.back;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.health.entity.system.User;
import com.health.service.system.DeliveryAddressService;
import com.health.service.system.MemberCancelService;
import com.health.service.system.MemberJoinService;
import com.health.service.system.MemberRenewService;
import com.health.service.system.MemberService;
import com.health.system.util.DateUtil;
import com.health.system.util.PageData;

@Controller
public class MemberController
  extends BaseController
{
  String menuUrl = "/back/listMember";
  @Resource(name="memberService")
  private MemberService memberService;
  @Resource(name="memberRenewService")
  private MemberRenewService memberRenewService;
  @Resource(name="memberCancelService")
  private MemberCancelService memberCancelService;
  @Resource(name="deliveryAddressService")
  private DeliveryAddressService deliveryAddressService;
  @Resource(name="memberJoinService")
  private MemberJoinService memberJoinService;
  
  @RequestMapping({"/back/listMember"})
  public ModelAndView listMember(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    pd = getPageData();
    
    String memberNo = pd.getString("memberNo");
    String phone = pd.getString("phone");
    String name = pd.getString("name");
    String typeId = pd.getString("typeId");
    if ((typeId != null) && (!"".equals(typeId))) {
      pd.put("memberType", typeId);
    }
    if ((phone != null) && (!"".equals(phone)))
    {
      phone = phone.trim();
      pd.put("phone", phone);
    }
    if ((name != null) && (!"".equals(name)))
    {
      name = name.trim();
      pd.put("memberName", name);
    }
    if ((memberNo != null) && (!"".equals(memberNo)))
    {
      memberNo = memberNo.trim();
      pd.put("memberNo", memberNo);
    }
    page.setPd(pd);
    List<PageData> MemberList = this.memberService.getDatalistPage(page);
    List<PageData> typeList = this.memberService.getMemberTypeList();
    
    mv.setViewName("back/member/member_list");
    mv.addObject("memberList", MemberList);
    mv.addObject("typeList", typeList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    return mv;
  }
  
  @RequestMapping({"/back/toMemberView"})
  public ModelAndView toMemberView(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = getPageData();
    
    String id = getRequest().getParameter("id");
    pd = this.memberService.getMemberById(id);
    
    PageData addressPd = new PageData();
    addressPd.put("memberId", id);
    page.setPd(addressPd);
    List<PageData> addresslist = this.deliveryAddressService.getDatalistPage(page);
    
    mv.addObject("pd", pd);
    mv.addObject("memberAddressList", addresslist);
    mv.setViewName("back/member/member_view");
    
    return mv;
  }
  
  @RequestMapping({"/back/toMemberEdit"})
  public ModelAndView toEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      mv.setViewName("back/member/member_edit");
      pd = getPageData();
      pd = this.memberService.getMemberById(pd.getString("id"));
      List<PageData> typeList = this.memberService.getMemberTypeList();
      mv.addObject("typeList", typeList);
      mv.addObject("pd", pd);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateMember"})
  public String updateMember()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      pg.put("editime", new Date());
      this.memberService.updateMember(pg);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listMember";
  }
  
  public Map<String, String> getHC()
  {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    return (Map)session.getAttribute("QX");
  }
  
  @RequestMapping({"/back/listMemberRenew"})
  public ModelAndView toMemberRenewList(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = getPageData();
    
    String typeId = pd.getString("typeId");
    if ((typeId == null) || (typeId == "")) {
      pd.put("typeId", "1");
    }
    page.setPd(pd);
    List<PageData> memberRenewList = this.memberRenewService.getDatalistPage(page);
    
    mv.addObject("memberRenewList", memberRenewList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    mv.setViewName("back/member/member_renew_list");
    return mv;
  }
  
  @RequestMapping({"/back/toMemberRenewView"})
  public ModelAndView toRenewView()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      String id = getRequest().getParameter("id");
      pd = this.memberRenewService.getMemberRenewById(id);
      mv.addObject("pd", pd);
      mv.setViewName("back/member/member_renew_view");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/toMemberRenewEdit"})
  public ModelAndView toRenewEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      String id = getRequest().getParameter("id");
      pd = this.memberRenewService.getMemberRenewById(id);
      mv.addObject("pd", pd);
      mv.setViewName("back/member/member_renew_edit");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateMemberRenew"})
  public String updateMemberRenew()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      String step = pg.get("step").toString();//续约申请处理的步骤
      if (step.equals("1"))//续约申请中
      {
        String status = pg.get("status").toString();
        if (status.equals("1"))//申请成功
        {
          pg.put("status", Integer.valueOf(2));
        }
        else if (status.equals("2"))//申请失败
        {
          String failReason = pg.getString("failReason");
          pg.put("status", Integer.valueOf(3));
          pg.put("failReason", failReason);
        }
      }
      else if (step.equals("2"))//续约申请成功
      {
        pg.put("status", Integer.valueOf(4));
      }
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      User user = (User)session.getAttribute("sessionUser");
      pg.put("operator", user.getNAME());
      pg.put("dealTime", new Date());
      this.memberRenewService.updateMemberRenew(pg);
      
    //修改会员的有效期
      if (pg.get("status").toString().equals("4"))//签约
      {
        Integer years = Integer.valueOf(Integer.parseInt(pg.get("requestTerm").toString()));
      //判断会员是否已过有效期
        PageData member = this.memberService.getMemberById(pg.getString("memberId"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date expireDate = format.parse(member.get("expireDate").toString());
        Date now = new Date();
        Date expireDateNew;
        if (now.getTime() > expireDate.getTime()) {//已过期，从当前时间往后延续
          expireDateNew = DateUtil.getAfterDayDate(now, Integer.valueOf(365 * years.intValue()));
        } else {//未过期，从过期日后面一天往后延期
          expireDateNew = DateUtil.getAfterDayDate(expireDate, Integer.valueOf(365 * years.intValue() + 1));
        }
        member.put("period", Integer.valueOf(365 * years.intValue()));
        member.put("expireDate", expireDateNew);
        this.memberService.updateMember(member);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listMemberRenew";
  }
  
  @RequestMapping({"/back/listMemberCancel"})
  public ModelAndView toMemberCancelList(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = getPageData();
    String typeId = pd.getString("typeId");
    if ((typeId == null) || (typeId == "")) {
      pd.put("typeId", "1");
    }
    page.setPd(pd);
    List<PageData> memberCancelList = this.memberCancelService.getDatalistPage(page);
    
    mv.addObject("memberCancelList", memberCancelList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    mv.setViewName("back/member/member_cancel_list");
    return mv;
  }
  
  @RequestMapping({"/back/toMemberCancel"})
  public ModelAndView toMemberCancel()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = getPageData();
    
    String id = getRequest().getParameter("id");
    pd = this.memberCancelService.getMemberCancelById(id);
    
    mv.addObject("pd", pd);
    mv.setViewName("back/member/member_cancel_view");
    
    return mv;
  }
  
  @RequestMapping({"/back/toMemberCancelEdit"})
  public ModelAndView toRenewCancelEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      String id = getRequest().getParameter("id");
      pd = this.memberCancelService.getMemberCancelById(id);
      mv.addObject("pd", pd);
      mv.setViewName("back/member/member_cancel_edit");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateMemberCancel"})
  public String updateMemberCancel()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      String step = pg.get("step").toString();//续约申请处理的步骤
      if (step.equals("1"))//解约申请中
      {
        String status = pg.get("status").toString();
        if (status.equals("1"))//申请成功
        {
          pg.put("status", Integer.valueOf(2));
        }
        else if (status.equals("2"))//申请失败
        {
          String failReason = pg.getString("failReason");
          pg.put("status", Integer.valueOf(3));
          pg.put("failReason", failReason);
        }
      }
      else if (step.equals("2"))//解约申请成功
      {
        pg.put("status", Integer.valueOf(4));
      }
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      User user = (User)session.getAttribute("sessionUser");
      pg.put("operator", user.getNAME());
      pg.put("dealTime", new Date());
      this.memberCancelService.updateMemberCancel(pg);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listMemberCancel";
  }
  
  @RequestMapping({"/back/listMemberJoin"})
  public ModelAndView toMemberJoinList(Page page)
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = getPageData();
    String typeId = pd.getString("typeId");
    if ((typeId == null) || (typeId == "")) {
      pd.put("typeId", "1");
    }
    page.setPd(pd);
    List<PageData> memberJoinList = this.memberJoinService.getDatalistPage(page);
    
    mv.addObject("memberJoinList", memberJoinList);
    mv.addObject("pd", pd);
    mv.addObject("QX", getHC());
    mv.setViewName("back/member/member_join_list");
    return mv;
  }
  
  @RequestMapping({"/back/toMemberJoin"})
  public ModelAndView toMemberJoin()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    PageData pd = getPageData();
    
    String id = getRequest().getParameter("id");
    pd = this.memberJoinService.getMemberJoinById(id);
    
    mv.addObject("pd", pd);
    mv.setViewName("back/member/member_join_view");
    
    return mv;
  }
  
  @RequestMapping({"/back/toMemberJoinEdit"})
  public ModelAndView toRenewJoinEdit()
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    
    PageData pd = new PageData();
    try
    {
      String id = getRequest().getParameter("id");
      pd = this.memberJoinService.getMemberJoinById(id);
      mv.addObject("pd", pd);
      mv.setViewName("back/member/member_join_edit");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping({"/back/updateMemberJoin"})
  public String updateMemberJoin()
    throws Exception
  {
    PageData pg = new PageData();
    try
    {
      pg = getPageData();
      pg.put("status", Integer.valueOf(2));
      
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      User user = (User)session.getAttribute("sessionUser");
      pg.put("operator", user.getNAME());
      pg.put("dealTime", new Date());
      this.memberJoinService.updateMemberJoin(pg);
      
      String memberId = pg.getString("memberId");
      PageData member = new PageData();
      member.put("id", memberId);
      member.put("memberType", Integer.valueOf(2));//将会员类型更改为经销会员
      this.memberService.updateMember(member);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error(e.toString(), e);
    }
    return "redirect:/back/listMemberJoin";
  }
}