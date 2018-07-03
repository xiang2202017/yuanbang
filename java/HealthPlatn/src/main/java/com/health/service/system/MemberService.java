package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Member;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("memberService")
public class MemberService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除会员
	public void deleteMemberById(Integer id) throws Exception{
		dao.delete("MemberMapper.delete", id);			//删除会员
	}
	
	//批量删除会员
	public void deleteAllMembers(String[] ids) throws Exception{
		dao.delete("MemberMapper.deleteAll", ids);
	}
	
	//添加会员
	public Integer insertMember(PageData pd) throws Exception{
		return (Integer)dao.save("MemberMapper.save", pd);
	}
	
	//根据id查找会员信息
	public PageData getMemberById(String id) throws Exception{
		return (PageData)dao.findForObject("MemberMapper.findById", id);
	}
	
	//根据电话查找会员信息
	public PageData getMemberByPhone(String phone) throws Exception{
		return (PageData)dao.findForObject("MemberMapper.findByPhone", phone);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MemberMapper.datalistPage", page);
	}
	
	//编辑会员信息
	public void updateMember(PageData pd) throws Exception{
		dao.update("MemberMapper.update", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getMemberTypeList() throws Exception{
		return (List<PageData>)dao.findForList("MemberTypeMapper.listAll","");
	}
	
	public String getTypeName(Integer id) throws Exception{
		return (String)dao.findForObject("MemberTypeMapper.getNameById", id);
	}
	
	/*
	* 登录判断
	*/
	public PageData getMemberByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberMapper.getMemberInfo", pd);
	}
	/*
	* 更新登录时间
	*/
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("MemberMapper.updateLastLogin", pd);
	}
	
	/**
	 * 检查会员是否唯一
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getMemberInfoForUnique(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.getMemberInfoForUnique", pd);
	}
	
	/**
	 * 修改密码
	 * @param pd
	 * @throws Exception
	 */
	public void updatePassword(PageData pd) throws Exception{
		dao.update("MemberMapper.updatePassword", pd);
	}
	
	/**
	 * 修改手机号码
	 * @param pd
	 * @throws Exception
	 */
	public void updatePhone(PageData pd) throws Exception{
		dao.update("MemberMapper.updatePhone", pd);
	}
	
	///////////////////////////////////短信验证码/////////////////////////////////
	
	/**
	 * 更新短信验证码发送次数，有则更新，无则添加
	 * @param pd
	 * @throws Exception
	 */
	public void updatePhoneSendNum(PageData pd) throws Exception{
		String hasrecord = pd.get("hasrecord").toString();
		if(hasrecord.equals("y")){
			dao.update("phoneSendNumMapper.update", pd);
		}else{
			insertPhoneSendNum(pd);
		}
	}
	
	public void updatePhoneSendNum_sub(PageData pd) throws Exception{
		dao.update("phoneSendNumMapper.update", pd);
	}
	
	private void insertPhoneSendNum(PageData pd) throws Exception{
		dao.save("phoneSendNumMapper.save", pd);
	}
	
	public void deletePhoneSendNum(String phone) throws Exception{
		dao.delete("phoneSendNumMapper.delete", phone);
	}
	
	public PageData selectPhoneSendNum(String phone) throws Exception{
		return (PageData)dao.findForObject("phoneSendNumMapper.findbyphone", phone);
	}
	
	/**
	 * 查询今天过期的会员
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getMembersByExpireDate(String date) throws Exception {
		return (List<Member>)dao.findForList("MemberMapper.getMembersByExpireDate", date);
	}
	
	/**
	 * 查询还有n天过期的会员
	 * @param type : 1还没有进行解约或续约申请    2：已经进行申请
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getMembersInNDays(String now,String expire, int type) throws Exception {
		JSONObject param = new JSONObject();
		param.put("now", now);
		param.put("expire", expire);
		param.put("type", type);
		return (List<Member>)dao.findForList("MemberMapper.getMembersInNDays", param);
	}

	public void updateStatus(int id) throws Exception {
		dao.update("MemberMapper.updateStatus", id);
	}
}
