package com.health.controller.front;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.MsgService;
import com.health.system.util.CustomUtil;
import com.health.system.util.PageData;

@Controller
public class MsgFController
  extends BaseController
{
  @Resource(name="msgService")
  private MsgService msgService;
  
  @RequestMapping({"/web/member/toMsg"})
  public String toMsg(ModelMap map, Page page)
    throws Exception
  {
    try
    {
      Subject currentUser = SecurityUtils.getSubject();
      Session session = currentUser.getSession();
      PageData member = (PageData)session.getAttribute("sessionMember");
      
      PageData pd = new PageData();
      pd.put("toType", member.get("memberType"));
      pd.put("memberNos", member.get("id").toString());
      page.setPd(pd);
      List<PageData> msglist = this.msgService.datalistPageForMember(page);
      map.put("msglist", msglist);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "front/member/member_msg";
  }
  
  @RequestMapping({"/web/member/toMsgDetail"})
  public String toMsgDetail(ModelMap map)
    throws Exception
  {
    try
    {
      String msgId = getRequest().getParameter("id");
      PageData pd = this.msgService.selectDetailById(msgId);
      String ctime = pd.getString("creatime");
      String etime = pd.getString("editime");
      String timestr = (etime != null) && (!etime.equals("")) ? pd.getString("editime") : ctime;
      String imgpath = pd.getString("imgPath");
      String headstr = CustomUtil.getMsgHeader(pd.getString("title"), imgpath, timestr);
      map.put("content", headstr + pd.getString("content"));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "front/member/member_msg_detail";
  }
}
