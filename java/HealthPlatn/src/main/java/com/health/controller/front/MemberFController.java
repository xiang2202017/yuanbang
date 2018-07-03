package com.health.controller.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.DeliveryAddressService;
import com.health.service.system.MemberCancelService;
import com.health.service.system.MemberJoinService;
import com.health.service.system.MemberRenewService;
import com.health.service.system.MemberService;
import com.health.system.interceptor.shiro.CustomizedToken;
import com.health.system.tools.sms.SendSmsUtil;
import com.health.system.tools.sms.entity.SendSmsResponse;
import com.health.system.util.Const;
import com.health.system.util.DateUtil;
import com.health.system.util.NumberUtil;
import com.health.system.util.PageData;
import com.health.system.util.Tools;

@Controller
public class MemberFController extends BaseController{

	@Resource(name="memberService")
	private MemberService memberService;
	@Resource(name="memberRenewService")
	MemberRenewService renewService;
	@Resource(name="memberCancelService")
	MemberCancelService cancelService;
	@Resource(name="deliveryAddressService")
	DeliveryAddressService deliveryAddressService;
	@Resource(name="memberJoinService")
	MemberJoinService memberJoinService;
	
	/**
	 * 跳转到会员中心
	 * @return
	 */
	@RequestMapping("/web/member/toMember")
	public String toMember(){
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.getSession().getAttribute(Const.SESSION_MEMBER) == null) {//没有进行过登录验证
			return "front/member/member_login";
        }else{
        	return "redirect:/web/member/toMemberMain";
        }
	}
	
	/**
	 * 会员登录
	 * @return
	 */
	@RequestMapping(value="/web/member/member_login")
	@ResponseBody
	public Object memberLogin() throws Exception{
		String result = "";
		try{
			PageData pd = this.getPageData();
			String memberNo = pd.getString("no");
			String password = pd.getString("pwd");
			password = new SimpleHash("SHA-1", memberNo, password).toString();	//密码加密
			pd.put("memberNo", memberNo);
			pd.put("password", password);
			PageData member = memberService.getMemberByNameAndPwd(pd);
			if(member != null){
				pd.put("lastLogin",DateUtil.getTime().toString());
				pd.put("id", member.get("id"));
				memberService.updateLastLogin(pd);
				
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				session.setAttribute(Const.SESSION_MEMBER, member);
				
				//shiro加入身份验证
				Subject subject = SecurityUtils.getSubject(); 
			    CustomizedToken token = new CustomizedToken(memberNo, password, Const.LOGIN_TYPE_USER); 
			    try { 
			        subject.login(token); 
			        result = "success";
			    } catch (AuthenticationException e) { 
			    	result = "roleerror";
			    }
			}else{
				result = "usererror";
			}
		}catch(Exception e){
			e.printStackTrace();
			result = "apperror";
		}
		Map<String, String> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	@RequestMapping(value="/web/member/to_member_register")
	public String toMemberRegister(ModelMap map){
		map.put("agreement", Tools.readTxtFileAll("admin/config/agreement.txt"));
		return "front/member/member_register";
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value="/web/member/memberLoginOut")
	public String memberLoginOut(){
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.getSession().setAttribute(Const.SESSION_MEMBER, null);
		return "front/member/member_login";
	}

///////////////////////////////////////////////////////////////////会员注册///////////////////////////////////////
	
	/**
	 * 公用方法：短信发送接口
	 * @param phone
	 * @param signName
	 * @param templateCode
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> sendSMS(String phone, String signName, String templateCode) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		try{
			//判断电话号码或者身份证是否已注册
			PageData pd = new PageData();
			pd.put("phone", phone);
			
			//判断今天发送的最大次数
			PageData phonesend = memberService.selectPhoneSendNum(phone);
			Integer sendnum = 0;
			String hasrecord = "n";
			if(phonesend != null){
				sendnum = Integer.parseInt(phonesend.get("sendnum").toString());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		        Date d=format.parse(phonesend.get("createTime").toString());
		        if(DateUtil.isToday(d)){//今天的发送次数
		        	if(sendnum >= Const.SMS_SEND_MAX_NUM){//如果达到今天发送的最大次数
		        		map.put("result", "numerror");
						map.put("msg", "已经达到今天的最大发送次数");
						return map;
		        	}
		        	hasrecord = "y";
		        }else{//删掉今天之前的发送次数记录
		        	memberService.deletePhoneSendNum(phone);
		        	sendnum = 0;
		        }
			}
			
			String num = Double.toString((Math.random()*9+1)*100000);	//生成6位短信验证码
			num = num.substring(0, num.indexOf('.'));
			String param = "{\"number\":"+ num +"}";
			SendSmsResponse reponse = SendSmsUtil.sendCodeSms(phone, param, signName, templateCode);
			if(reponse != null && reponse.getCode().equals("OK")){
				//将验证码放入session
				Subject currentUser = SecurityUtils.getSubject();
				currentUser.getSession().setAttribute(phone, num);
				map.put("result", "success");
				
				//更新号码当日发送验证码次数
				PageData phonesendPd = new PageData();
				phonesendPd.put("hasrecord", hasrecord);
				phonesendPd.put("phone", phone);
				phonesendPd.put("createTime", new Date());
				phonesendPd.put("sendnum", ++sendnum);
				memberService.updatePhoneSendNum(phonesendPd);
			}else{
				map.put("result", "error");
				map.put("msg", "发送失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "程序发生错误");
		}
		return map;
	}
	
	/**
	 * 获取短信验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/member_getCode",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object getCode() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String phone = getRequest().getParameter("phone"); 
		PageData pd = new PageData();
		pd.put("phone", phone);
		List<PageData> mlist = memberService.getMemberInfoForUnique(pd);
		if(mlist.size() > 0){
			map.put("result", "phoneerror");
			map.put("msg", "电话号码已存在");
			return map;
		}
		map = sendSMS(phone, Const.SMS_SIGN_NAME,Const.SMS_TEMPLATE_CODE);
		return map;
	}
	
	/**
	 * 会员注册
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/member_register",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object memberRegister() throws Exception{
		String result = "";
		Map<String,String> map = new HashMap<String,String>();
		try{
			PageData pd = new PageData();
			String memberName = this.getRequest().getParameter("name");
			String password = this.getRequest().getParameter("pwd");
			String idcardNo = this.getRequest().getParameter("idcardNo");
			String phone = this.getRequest().getParameter("phone");
			String address = this.getRequest().getParameter("address");
			String sex = this.getRequest().getParameter("sex");
			String company = this.getRequest().getParameter("company");
			String code = this.getRequest().getParameter("code");
			
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String sessioncode = session.getAttribute(phone).toString();
			if(!sessioncode.equals(code)){
				result = "codeerror";
			}else{
				//判断验证码是否失效
				PageData phonesend = memberService.selectPhoneSendNum(phone);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		        Date d=format.parse(phonesend.get("createTime").toString());
		        int minutes = DateUtil.getDisMinute(d, new Date());
				if(minutes > Const.SESSION_SMS_TIME){
					result = "timeerror";
					session.removeAttribute(phone);//移除掉存储的手机短信验证码
					map.put("result", result);
					return map;
				}
				
				String memberNo = NumberUtil.getMemberCarNo(memberName);
		        password = new SimpleHash("SHA-1", memberNo, password).toString();	//密码加密
				Date expireDate = DateUtil.getAfterDayDate(new Date(), 365);
				
				pd.put("memberName", memberName);
				pd.put("memberType", Const.MEMBER_TYPE_CUSTOMER);
				pd.put("lastLogin", DateUtil.getTime().toString());
				pd.put("idcardNo", idcardNo);
				pd.put("password", password);
				pd.put("phone", phone);
				pd.put("address", address);
				pd.put("sex", sex);
				pd.put("company", company);
				pd.put("status", Const.MEMBER_STATUS_NORMAL);
				pd.put("createTime", new Date());
				pd.put("period", 365);
				pd.put("memberNo", memberNo);
				pd.put("expireDate", expireDate);
				
				//判断电话号码或者身份证是否已注册
				List<PageData> mlist = memberService.getMemberInfoForUnique(pd);
				if(mlist.size() > 0){
					result = "exist";
				}else{
					memberService.insertMember(pd);
					//pd.put("id", id);
					//shiro管理的session
					session.setAttribute(Const.SESSION_MEMBER, pd);
					session.removeAttribute(phone);//移除掉存储的手机短信验证码
					result = "success";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result = "apperror";
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 跳转到会员主页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/web/member/toMemberMain")
	public String toMemberMain(ModelMap map){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		PageData member = (PageData)session.getAttribute(Const.SESSION_MEMBER);
		map.put("member", member);
		return "front/member/member_main";
	}
	
	/**
	 * 跳转到会员基本信息页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/web/member/toMemberInfo")
	public String toMemberInfo(ModelMap map){
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		PageData pd = (PageData)session.getAttribute(Const.SESSION_MEMBER);
		map.put("member", pd);
		return "front/member/member_info_detail";
	}
	
	/**
	 * 跳转到密码修改页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/web/member/toMemberPassword")
	public String toMemberPassword(){
		return "front/member/member_info_password";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(value="/web/member/updateMemberPassword")
	@ResponseBody
	public Object updateMemberPassword(){
		String result = "";
		try{
			PageData pd = new PageData();
			String oripwd = this.getRequest().getParameter("idoripwd");		//用户输入的原始密码
			String password = this.getRequest().getParameter("newpwd");
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			PageData member = (PageData)session.getAttribute(Const.SESSION_MEMBER);
			
			//判断输入的原始密码是否正确
			oripwd = new SimpleHash("SHA-1", member.get("memberNo").toString(), oripwd).toString();	//密码加密
			if(!oripwd.equals(member.getString("password"))){
				result = "pwderror";
			}else{
				Integer id = Integer.parseInt(member.get("id").toString());
				password = new SimpleHash("SHA-1", member.get("memberNo").toString(), password).toString();	//密码加密
				pd.put("id", id);
				pd.put("password", password);
				memberService.updatePassword(pd);
				member.put("password", password);
				result = "success";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			result = "apperror";
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("result", result);
		return map;
	}
	
	/**
	 * 跳转到手机修改页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/web/member/toMemberPhone")
	public String toMemberPhone(){
		return "front/member/member_info_phone";
	}
	
	/**
	 * 发送验证码--更改手机号码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/member_getChgPhoneCode")
	public Object getCodeForPhoneChg() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String phone = getRequest().getParameter("phone"); 
		map = sendSMS(phone, Const.SMS_SIGN_NAME,Const.SMS_TEMPLATE_PHONE);
		return map;
	}
	
	/**
	 * 修改手机号码
	 * @return
	 */
	@RequestMapping(value="/web/member/updateMemberPhone")
	@ResponseBody
	public Object updateMemberPhone(){
		Map<String,String> map = new HashMap<String, String>();
		String result = "";
		try{
			String phone = this.getRequest().getParameter("phone");
			String code = this.getRequest().getParameter("code");
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			PageData member = (PageData)session.getAttribute(Const.SESSION_MEMBER);
			
			//判断验证码
			String sessioncode = session.getAttribute(phone).toString();
			if(!sessioncode.equals(code)){
				result = "codeerror";
			}else{
				//判断验证码是否失效
				PageData phonesend = memberService.selectPhoneSendNum(phone);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		        Date d=format.parse(phonesend.get("createTime").toString());
		        int minutes = DateUtil.getDisMinute(d, new Date());
				if(minutes > Const.SESSION_SMS_TIME){
					result = "timeerror";
					session.removeAttribute(phone);//移除掉存储的手机短信验证码
					map.put("result", result);
					return map;
				}
			
				Integer id = Integer.parseInt(member.get("id").toString());
				PageData pd = new PageData();
				pd.put("id", id);
				pd.put("phone", phone);
				memberService.updatePhone(pd);
				result = "success";
			}
		}catch(Exception e){
			e.printStackTrace();
			result = "apperror";
		}
		map.put("result", result);
		return map;
	}
	//////////////////////////////////////////////////会员加盟/////////////////////////////////////////////////////////////////////////
	/**
	 * 跳转到会员加盟申请页面
	 * @return
	 */
	@RequestMapping(value="/web/member/toMemberJoin")
	public String toMemberJoin(Page page, ModelMap map) throws Exception{
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
		//如果用户失效，重新跳转到登录页面
		
		//判断会员是否有加盟资格
		
		Integer memberId = Integer.parseInt(member.get("id").toString());
		pd.put("memberId", memberId);
		pd.put("memberName", member.get("memberName"));
		pd.put("memberNo", member.get("memberNo"));
		List<PageData> joinlist = memberJoinService.getLastList(memberId.toString());
		PageData currJoin = null;
		if(joinlist != null && joinlist.size() != 0){
			currJoin = joinlist.get(0);
			String status = currJoin.get("status").toString();
			if(!status.equals("1") ){
				currJoin = null;
			}
		}
		
		map.put("currJoin", currJoin);
		map.put("joinlist", joinlist);
		map.put("member", member);
		return "front/member/member_join";
	}
	
	/**
	 * 添加会员加盟申请
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/addMemberJoin")
	@ResponseBody
	public Object addMemberJoin() throws Exception{
		String msg = "";
		try{
			Subject currentUser = SecurityUtils.getSubject();
			PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
			Integer memberId = Integer.parseInt(member.get("id").toString());
			String message = getRequest().getParameter("message").toString();
			String mail = getRequest().getParameter("mail").toString();
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			PageData pd = new PageData();
			pd.put("memberId", memberId);
			pd.put("memberName", member.get("memberName"));
			pd.put("memberNo", member.get("memberNo"));
			pd.put("phone", member.get("phone"));
			pd.put("idcardNo", member.get("idcardNo"));
			pd.put("createTime", sdf.format(new Date()));
			pd.put("status", 1);
			pd.put("message", message);
			pd.put("mail", mail);
			memberJoinService.insertMemberJoin(pd);
			msg = "success";
		}catch(Exception e){
			e.printStackTrace();
			msg = "apperror";
		}
		Map<String,String> result = new HashMap<String, String>();
		result.put("result", msg);
		return result;
	}
	
	//////////////////////////////////////////////////会员续约开始//////////////////////////////////////////////////////////////////////
	
	/**
	 * 跳转到会员续约申请页面
	 * @return
	 */
	@RequestMapping(value="/web/member/toMemberRenew")
	public String toMemberRenew(Page page, ModelMap map) throws Exception{
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
		//如果用户失效，重新跳转到登录页面
		
		Integer memberId = Integer.parseInt(member.get("id").toString());
		pd.put("memberId", memberId);
		//PageData currRenew = renewService.getMemberRenewByMemberId(memberId.toString());
		List<PageData> renewlist = renewService.getLastList(memberId.toString());
		PageData currRenew = null;
		if(renewlist != null && renewlist.size() != 0){
			currRenew = renewlist.get(0);
			String status = currRenew.get("status").toString();
			if(status.equals(Const.MEMBER_RENEW_STATUS_DONE) && status.equals(Const.MEMBER_RENEW_STATUS_EXPIRE) && status.equals(Const.MEMBER_RENEW_STATUS_FAIL)){
				currRenew = null;
			}
		}
		
		map.put("renew", currRenew);
		map.put("renewlist", renewlist);
		map.put("member", member);
		return "front/member/member_renew";
	}
	
	/**
	 * 添加会员续约
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/memberRenew")
	@ResponseBody
	public Object addMemberRenew() throws Exception{
		String msg = "";
		try{
			Subject currentUser = SecurityUtils.getSubject();
			PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
			Integer memberId = Integer.parseInt(member.get("id").toString());
			Integer requestTerm = Integer.parseInt(getRequest().getParameter("term").toString());
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			PageData pd = new PageData();
			pd.put("memberId", memberId);
			pd.put("memberName", member.get("memberName"));
			pd.put("memberNo", member.get("memberNo"));
			pd.put("requestTerm", requestTerm);
			pd.put("createTime", sdf.format(new Date()));
			pd.put("status", 1);
			renewService.insertMemberRenew(pd);
			msg = "success";
		}catch(Exception e){
			e.printStackTrace();
			msg = "apperror";
		}
		Map<String,String> result = new HashMap<String, String>();
		result.put("result", msg);
		return result;
	}
	
	//////////////////////////////////////////////////////////////////////会员解约开始//////////////////////////////////////////////////////////////
	/**
	 * 跳转到会员解约界面
	 * @param page
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/toMemberCancel")
	public String toMemberCancel(ModelMap map) throws Exception{
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
		Integer memberId = Integer.parseInt(member.get("id").toString());
		pd.put("memberId", memberId);
		//PageData memberCancel = cancelService.getMemberCancelByMemberId(memberId.toString());
		List<PageData> cancellist = cancelService.getLastList(memberId.toString());
		PageData currCancel = null;
		if ((cancellist != null) && (cancellist.size() != 0))
	    {
	      currCancel = (PageData)cancellist.get(0);
	      String status = currCancel.get("status").toString();
	      if ((status.equals("4")) && (status.equals("5")) && (status.equals("3"))) {
	        currCancel = null;
	      }
	    }
		map.put("cancel", currCancel);
		map.put("cancellist", cancellist);
		map.put("member", member);
		return "front/member/member_cancel";
	}
	
	/**
	 * 添加会员解约申请
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/memberCancle")
	@ResponseBody
	public Object addMemberCancle() throws Exception{
		String msg = "";
		try{
			Subject currentUser = SecurityUtils.getSubject();
			PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
			Integer memberId = Integer.parseInt(member.get("id").toString());
			String reason = getRequest().getParameter("reason");
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			PageData pd = new PageData();
			pd.put("memberId", memberId);
			pd.put("memberName", member.get("memberName"));
			pd.put("memberNo", member.get("memberNo"));
			pd.put("status", 1);
			pd.put("cancelReason", reason);
			pd.put("createTime", sdf.format(new Date()));
			cancelService.insertMemberCancel(pd);
			msg = "success";
		}catch(Exception e){
			e.printStackTrace();
			msg = "apperror";
		}
		Map<String,String> result = new HashMap<String, String>();
		result.put("result", msg);
		return result;
	}
	
	///////////////////////////////////////////////////////////////////////会员收货地址/////////////////////////////////////////
	/**
	 * 跳转到会员收货地址页面
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/toAddress")
	public String toAddress(ModelMap map, Page page) throws Exception{
		try{
			PageData pd = new PageData();
			Subject currentUser = SecurityUtils.getSubject();
			PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
			Integer memberId = Integer.parseInt(member.get("id").toString());
			pd.put("memberId", memberId);
			page.setPd(pd);
			List<PageData> addressList = deliveryAddressService.getDatalistPage(page);
			map.addAttribute("addressList", addressList);
			map.put("type", "view");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "front/member/member_info_address";
	}
	
	/**
	 * 跳转到地址修改页面
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/toChgAddress")
	public String toChgAddress(ModelMap map) throws Exception{
		try{
			String id = getRequest().getParameter("id").toString();
			PageData pd = new PageData();
			pd.put("id", id);
			PageData address = deliveryAddressService.getAddressById(id);
			map.put("type", "chg");
			map.put("address", address);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "front/member/member_info_address";
	}
	
	/**
	 * 删除会员地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/delAddress")
	@ResponseBody
	public Object delAddress() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		try{
			Integer id = Integer.parseInt(getRequest().getParameter("id").toString());
			deliveryAddressService.deleteAddressById(id);
			map.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			map.put("result", "fail");
		}
		return map;
	}
	
	/**
	 * 增加会员地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/addAddress")
	@ResponseBody
	public Object addAddress() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		try{
			Subject currentUser = SecurityUtils.getSubject();
			PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
			Integer memberId = Integer.parseInt(member.get("id").toString());
			
			String name = getRequest().getParameter("name");
			String phone = getRequest().getParameter("phone");
			String address = getRequest().getParameter("address");
			String isdefault = getRequest().getParameter("isDefault");
			String postCode = getRequest().getParameter("postCode");
			
			PageData pd1 = deliveryAddressService.findDefaultAddress(memberId.toString());
			if(pd1 != null){
				if(isdefault.equals("1")){
					pd1.put("isDefault", "2");
					deliveryAddressService.updateAddress(pd1);
				}
			}else{
				isdefault = "1";
			}
			
			PageData pd = new PageData();
			pd.put("name", name);
			pd.put("phone", phone);
			pd.put("address", address);
			pd.put("isDefault", isdefault);
			pd.put("postCode", postCode);
			pd.put("memberId", memberId);
			
			deliveryAddressService.insertAddress(pd);
			map.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			map.put("result", "fail");
		}
		return map;
	}
	
	/**
	 * 修改会员地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/chgAddress")
	@ResponseBody
	public Object chgAddress() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		try{
			Integer id = Integer.parseInt(getRequest().getParameter("id").toString());
			String name = getRequest().getParameter("name");
			String phone = getRequest().getParameter("phone");
			String address = getRequest().getParameter("address");
			String isdefault = getRequest().getParameter("isDefault");
			String postCode = getRequest().getParameter("postCode");
			
			PageData pd = new PageData();
			pd.put("name", name);
			pd.put("phone", phone);
			pd.put("address", address);
			pd.put("isDefault", isdefault);
			pd.put("postCode", postCode);
			pd.put("id", id);
			
			Subject currentUser = SecurityUtils.getSubject();
			PageData member = (PageData)currentUser.getSession().getAttribute(Const.SESSION_MEMBER);
			Integer memberId = Integer.parseInt(member.get("id").toString());
			if(isdefault.equals("1")){//将其他的默认地址去掉
				PageData pd1 = deliveryAddressService.findDefaultAddress(memberId.toString());
				if(pd1 != null){
					pd1.put("isDefault", "2");
					deliveryAddressService.updateAddress(pd1);
				}
			}
			
			deliveryAddressService.updateAddress(pd);
			map.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			map.put("result", "fail");
		}
		return map;
	}
	
	///////////////////////////////////////////////////会员忘记密码//////////////////////////////////////////////////////////////
	/**
	 * 跳转到忘记密码页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/web/member/to_member_forgetPwd")
	public String toForgetPwd(){
		return "front/member/member_forget_pwd";
	}
	
	/**
	 * 发送验证码--忘记密码--手机号码验证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/member/member_getForgetPwdCode")
	public Object getForgetPwdCode() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String phone = getRequest().getParameter("phone"); 
		map = sendSMS(phone, Const.SMS_SIGN_NAME,Const.SMS_TEMPLATE_PWD);
		return map;
	}
	
	/**
	 * 设置新密码 
	 * @return
	 */
	@RequestMapping(value="/web/member/updateMemberForgetPwd")
	@ResponseBody
	public Object updateMemberForgetPwd(){
		Map<String,String> map = new HashMap<String, String>();
		String result = "";
		try{
			String phone = this.getRequest().getParameter("phone");
			String code = this.getRequest().getParameter("code");
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			
			//判断验证码
			String sessioncode = session.getAttribute(phone).toString();
			if(!sessioncode.equals(code)){
				result = "codeerror";
			}else{
				//判断验证码是否失效
				PageData phonesend = memberService.selectPhoneSendNum(phone);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		        Date d=format.parse(phonesend.get("createTime").toString());
		        int minutes = DateUtil.getDisMinute(d, new Date());
				if(minutes > Const.SESSION_SMS_TIME){
					result = "timeerror";
					session.removeAttribute(phone);//移除掉存储的手机短信验证码
					map.put("result", result);
					return map;
				}
			
				PageData member = memberService.getMemberByPhone(phone);
				Integer id = Integer.parseInt(member.get("id").toString());
				String password = this.getRequest().getParameter("newPwd");
				password = new SimpleHash("SHA-1", member.get("memberNo").toString(), password).toString();	//密码加密
				PageData pd = new PageData();
				pd.put("id", id);
				pd.put("password", password);
				memberService.updatePassword(pd);
				
				result = "success";
			}
		}catch(Exception e){
			e.printStackTrace();
			result = "apperror";
		}
		map.put("result", result);
		return map;
	}
}
