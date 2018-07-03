package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Member;
import com.health.entity.system.MemberRenew;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("memberRenewService")
public class MemberRenewService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除会员续约
	public void deleteMemberRenewById(Integer id) throws Exception{
		dao.delete("MemberRenewMapper.delete", id);			//删除会员续约申请
	}
	
	//批量删除会员续约
	public void deleteAllMemberRenews(String[] ids) throws Exception{
		dao.delete("MemberRenewMapper.deleteAll", ids);
	}
	
	//添加会员续约
	public Integer insertMemberRenew(PageData pd) throws Exception{
		return (Integer)dao.save("MemberRenewMapper.save", pd);
	}
	
	//根据id查找会员续约信息
	public PageData getMemberRenewById(String id) throws Exception{
		return (PageData)dao.findForObject("MemberRenewMapper.findById", id);
	}
	
//	//根据id查找会员续约信息
//	public PageData getMemberRenewByMemberId(String id) throws Exception{
//		return (PageData)dao.findForObject("MemberRenewMapper.findByMemberId", id);
//	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MemberRenewMapper.datalistPage", page);
	}
	
	//获取最后5条数据
	@SuppressWarnings("unchecked")
	public List<PageData> getLastList(String id) throws Exception{
		return (List<PageData>)dao.findForList("MemberRenewMapper.getLastList", id);
	}
	
	//编辑会员续约信息
	public void updateMemberRenew(PageData pd) throws Exception{
		dao.update("MemberRenewMapper.update", pd);
	}

	@SuppressWarnings("unchecked")
	public List<MemberRenew> getNeedRenewMembers(List<Member> list) throws Exception {
		return (List<MemberRenew>)dao.findForList("MemberRenewMapper.getNeedRenewMembers", list);
	}
	
	public void updateALl(MemberRenew m) throws Exception {
		dao.update("MemberRenewMapper.updateALl", m);
	}
	
}
