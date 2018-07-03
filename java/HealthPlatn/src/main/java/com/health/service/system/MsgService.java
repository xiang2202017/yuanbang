package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("msgService")
public class MsgService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除系统消息
	public void deleteMsgById(Integer id) throws Exception{
		dao.delete("MsgDetailMapper.delete", id);		//删除资讯详情
		dao.delete("MsgMapper.delete", id);			//删除系统消息申请
	}
	
	//批量删除系统消息
	public void deleteAllMsgs(String[] ids) throws Exception{
		dao.delete("MsgDetailMapper.deleteAllMsg", ids);
		dao.delete("MsgMapper.deleteAll", ids);
	}
	
	//添加系统消息
	public Integer insertMsg(PageData pd) throws Exception{
		return (Integer)dao.save("MsgMapper.save", pd);
	}
	
	//根据id查找系统消息信息
	public PageData getMsgById(String id) throws Exception{
		return (PageData)dao.findForObject("MsgMapper.findById", id);
	}
	
	//根据会员id查找系统消息信息
	public PageData getMsgByMemberId(String id) throws Exception{
		return (PageData)dao.findForObject("MsgMapper.findByMemberId", id);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsgMapper.datalistPage", page);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> datalistPageForMember(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsgMapper.datalistPageForMember", page);
	}
	
	//编辑系统消息信息
	public void updateMsg(PageData pd) throws Exception{
		dao.update("MsgMapper.update", pd);
		dao.update("MsgDetailMapper.update", pd);
	}
	
	//添加系统消息
	public Integer insertMsgDetail(PageData pd) throws Exception{
		return (Integer)dao.save("MsgDetailMapper.save", pd);
	}
	
	//获取系统消息信息及详情
	public PageData selectDetailById(String id) throws Exception{
		return (PageData)dao.findForObject("MsgMapper.findDetailById", id);
	}
	
	//获取系统消息详情
	public PageData selectDetail(String id) throws Exception{
		return (PageData)dao.findForObject("MsgDetailMapper.findById", id);
	}
}
