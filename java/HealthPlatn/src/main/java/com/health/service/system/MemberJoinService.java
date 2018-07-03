package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

/**
 * 经销商加盟
 * @author XiangYu
 *
 */
@Service("memberJoinService")
public class MemberJoinService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除会员加盟
	public void deleteMemberJoinById(Integer id) throws Exception{
		dao.delete("MemberJoinMapper.delete", id);			//删除会员加盟申请
	}
	
	//批量删除会员加盟
	public void deleteAllMemberJoins(String[] ids) throws Exception{
		dao.delete("MemberJoinMapper.deleteAll", ids);
	}
	
	//添加会员加盟
	public Integer insertMemberJoin(PageData pd) throws Exception{
		return (Integer)dao.save("MemberJoinMapper.save", pd);
	}
	
	//根据id查找会员加盟信息
	public PageData getMemberJoinById(String id) throws Exception{
		return (PageData)dao.findForObject("MemberJoinMapper.findById", id);
	}
	
	//根据会员id查找会员加盟信息
	public PageData getMemberJoinByMemberId(String id) throws Exception{
		return (PageData)dao.findForObject("MemberJoinMapper.findByMemberId", id);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MemberJoinMapper.datalistPage", page);
	}
	
	//编辑会员加盟信息
	public void updateMemberJoin(PageData pd) throws Exception{
		dao.update("MemberJoinMapper.update", pd);
	}
	
	//获取最后5条数据
	@SuppressWarnings("unchecked")
	public List<PageData> getLastList(String id) throws Exception{
		return (List<PageData>)dao.findForList("MemberJoinMapper.getLastList", id);
	}
	
}
