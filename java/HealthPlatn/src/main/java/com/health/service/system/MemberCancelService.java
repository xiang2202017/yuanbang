package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Member;
import com.health.entity.system.MemberCancel;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("memberCancelService")
public class MemberCancelService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除会员解约
	public void deleteMemberCancelById(Integer id) throws Exception{
		dao.delete("MemberCancelMapper.delete", id);			//删除会员解约申请
	}
	
	//批量删除会员解约
	public void deleteAllMemberCancels(String[] ids) throws Exception{
		dao.delete("MemberCancelMapper.deleteAll", ids);
	}
	
	//添加会员解约
	public Integer insertMemberCancel(PageData pd) throws Exception{
		return (Integer)dao.save("MemberCancelMapper.save", pd);
	}
	
	//根据id查找会员解约信息
	public PageData getMemberCancelById(String id) throws Exception{
		return (PageData)dao.findForObject("MemberCancelMapper.findById", id);
	}
	
	//根据id查找会员解约信息
	public PageData getMemberCancelByMemberId(String id) throws Exception{
		return (PageData)dao.findForObject("MemberCancelMapper.findByMemberId", id);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MemberCancelMapper.datalistPage", page);
	}
	
	//获取最后5条数据
	@SuppressWarnings("unchecked")
	public List<PageData> getLastList(String id) throws Exception{
		return (List<PageData>)dao.findForList("MemberCancelMapper.getLastList", id);
	}
	
	//编辑会员解约信息
	public void updateMemberCancel(PageData pd) throws Exception{
		dao.update("MemberCancelMapper.update", pd);
	}

	@SuppressWarnings("unchecked")
	public List<MemberCancel> getNeedCancelMembers(List<Member> list) throws Exception {
		return (List<MemberCancel>)dao.findForList("MemberCancelMapper.getNeedCancelMembers", list);
	}
	
	public void updateALl(MemberCancel m) throws Exception {
		dao.update("MemberCancelMapper.updateAll", m);
	}
	
}
