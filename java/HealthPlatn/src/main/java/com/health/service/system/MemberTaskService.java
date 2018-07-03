package com.health.service.system;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.health.entity.system.Member;
import com.health.entity.system.MemberCancel;
import com.health.entity.system.MemberRenew;
import com.health.system.tools.sms.SendSmsUtil;
import com.health.system.util.Const;
import com.health.system.util.Logger;

/**
 * 定时查询会员情况
 * 
 * 1）如果发现会员10天后到期： a)后台自动发消息给该会员，提示会员一周之内登录自己帐号办理会员续约或解约申请。 b)同时发短信给会员提醒。
 * 2）如果发现会员已经过了会员有效期，依然没有进行会员续约或解约申请，则将会员的状态置为“已过期”
 * 3）对于已经通过续约或解约申请的会员，在会员过期前3天发短信给会员提醒会员到店里办理相关流程手续，同时自动发系统消息给该会员。 1.2
 * 定时扫描会员续约申请及解约申请表 1）如果申请的状态一直处于“申请中”，且超过了会员的有限期限，则将该申请状态变更为“已过期”
 * 2）如果会员申请状态在“申请成功
 * ”的情况下，一直没有去门店办理相关流程，申请的状态没有更换为“已续约”，且已经超过了会员的有限期限，则将该申请状态变更为“已过期”
 *
 */
@Component
@EnableScheduling
public class MemberTaskService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MemberCancelService cancelService;

	@Autowired
	private MemberRenewService renewService;

	@Autowired
	private MemberService memberService;

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 每天中午12点运行,发送短信提醒
	 */
	@Scheduled(cron = "0 0 12 * * ?")
	public void checkMemberStatus() {
		// 查看十天后到期的会员（没有进行解约或续约申请），发送短信提醒进行申请
		sendMessageBeforTenDays();
		// 查看三天后到期的会员，而且已经通过续约或解约申请的会员，发送短信提醒去门店办理
		sendMessageBeforTHreeDays();
	}

	/**
	 * 每天23:59:59运行，检查今天过期但没有进行解约续约的会员，设置为过期
	 */
	@Scheduled(cron = "59 59 23 * * ?")
	public void setMemberStatus() {
		changeMemberStatus();
	}
	
	/**
	 * 十天过期（但没有进行解约或续约申请的），发送信息提醒
	 */
	private void sendMessageBeforTenDays() {
		try {
			//查询到10天或者小于十天过期的会员,但还没有申请
			List<Member> list = memberService.getMembersInNDays(format.format(getNextDay(0)),format.format(getNextDay(10)),1);
			for (int i = 0; i < list.size(); i++) {
				// 发短信
				Member m = list.get(i);
				JSONObject param = new JSONObject();
				param.put("name", m.getMemberName());
				SendSmsUtil.sendCodeSms(m.getPhone(), param.toJSONString(), 
						                        Const.SMS_SIGN_NAME, Const.SMS_TEMPLATE_INFO);
			}
		} catch (Exception e) {
			logger.error("获取快过期会员失败", e);
		}
	}

	/**
	 * 已经通过申请但未办理，提前三天发送信息
	 */
	private void sendMessageBeforTHreeDays() {
		try {
			//查询到三天或者三天之内过期的会员，已经申请过
 			List<Member> list = memberService.getMembersInNDays(format.format(getNextDay(0)),format.format(getNextDay(3)),2);
			if(list == null || list.size()<=0){
				return;
			}
			//查询三天或三天之内过期而且还没有进行解约或者续约的会员
			//未解约
			List<MemberCancel> cancels = cancelService.getNeedCancelMembers(list);
			for (int i = 0; i < cancels.size(); i++) {
				// 发短信
				Member m = getMemberById(cancels.get(i).getMemberId(),list);
				if(m!=null){
					JSONObject param = new JSONObject();
					param.put("name", m.getMemberName());
					param.put("cardno", m.getMemberNo());
					param.put("type", "解约");
					SendSmsUtil.sendCodeSms(m.getPhone(), param.toJSONString(), 
	                        Const.SMS_SIGN_NAME, Const.SMS_TEMPLATE_INFO_OK);
				}
			}
			
			//未续约
			List<MemberRenew> renews = renewService.getNeedRenewMembers(list);
			for (int i = 0; i < renews.size(); i++) {
				// 发短信
				Member m = getMemberById(renews.get(i).getMemberId(),list);
				if(m!=null){
					JSONObject param = new JSONObject();
					param.put("name", m.getMemberName());
					param.put("cardno", m.getMemberNo());
					param.put("type", "续约");
					SendSmsUtil.sendCodeSms(m.getPhone(), param.toJSONString(), 
	                        Const.SMS_SIGN_NAME, Const.SMS_TEMPLATE_INFO_OK);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private Member getMemberById(Integer memberId, List<Member> list) {
		for(Member m: list){
			if(m.getId().intValue()==memberId.intValue()){
				return m;
			}
		}
		return null;
	}

	/**
	 * 将未申请的、过期未办理的、过期的都设置为“已过期”
	 */
	private void changeMemberStatus() {
		try {
			// 查询到今天和今天之前过期的会员
			List<Member> list = memberService.getMembersByExpireDate(format.format(new Date()));
			if (list == null || list.size()<=0) {
				return;
			}
			// 查询今天过期还没有进行解约或者续约的会员
			List<MemberCancel> cancels = cancelService.getNeedCancelMembers(list);
			List<MemberRenew> renews = renewService.getNeedRenewMembers(list);
			for (int i = 0; i < list.size(); i++) {
				memberService.updateStatus(list.get(i).getId());
			}
			for (int i = 0; i < cancels.size(); i++) {
				MemberCancel m = cancels.get(i);
				m.setStatus(2);
				cancelService.updateALl(m);
			}
			for (int i = 0; i < renews.size(); i++) {
				MemberRenew m = renews.get(i);
				m.setStatus(2);
				renewService.updateALl(m);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private Date getNextDay(int diff){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+diff);
		return c.getTime();
	}
	
}
