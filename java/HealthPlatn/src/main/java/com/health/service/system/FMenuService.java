package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.FMenu;
import com.health.system.util.PageData;

@Service("fmenuService")
public class FMenuService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void deleteMenuById(Integer MENU_ID) throws Exception {
		dao.save("FMenuMapper.deleteMenuById", MENU_ID);
		
	}

	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("FMenuMapper.getMenuById", pd);
		
	}
	
	public FMenu getMenuById(Integer menuid) throws Exception {
		return (FMenu) dao.findForObject("FMenuMapper.getFMenuById", menuid);
		
	}

	//取最大id
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("FMenuMapper.findMaxId", pd);
		
	}
	
	public List<FMenu> listAllParentMenu() throws Exception {
		return (List<FMenu>) dao.findForList("FMenuMapper.listAllParentMenu", null);
		
	}

	public void saveMenu(FMenu menu) throws Exception {
			//dao.update("FMenuMapper.updateMenu", menu);
		dao.save("FMenuMapper.insertMenu", menu);
	}

	public List<FMenu> listSubMenuByParentId(Integer parentId) throws Exception {
		return (List<FMenu>) dao.findForList("FMenuMapper.listSubMenuByParentId", parentId);
		
	}
		
	public List<FMenu> listAllMenu() throws Exception {
		List<FMenu> rl = this.listAllParentMenu();
		for(FMenu menu : rl){
			List<FMenu> subList = this.listSubMenuByParentId(menu.getMenu_id());
			menu.setSubMenu(subList);
		}
		return rl;
	}

	public List<FMenu> listAllSubMenu() throws Exception{
		return (List<FMenu>) dao.findForList("FMenuMapper.listAllSubMenu", null);
		
	}
	
	/**
	 * 编辑
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData)dao.findForObject("FMenuMapper.updateMenu", pd);
	}
	/**
	 * 保存菜单图标 (顶部菜单)
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData)dao.findForObject("FMenuMapper.editicon", pd);
	}
	
	/**
	 * 更新子菜单类型菜单
	 */
	public PageData editType(PageData pd) throws Exception {
		return (PageData)dao.findForObject("FMenuMapper.editType", pd);
	}
}
