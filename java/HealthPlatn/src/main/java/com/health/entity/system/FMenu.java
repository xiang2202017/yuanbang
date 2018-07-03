package com.health.entity.system;

import java.util.List;

/**
 * 前台菜单实体
 * @author XiangYu
 *
 */
public class FMenu implements java.io.Serializable{
	
	private static final long serialVersionUID = -3377470300833755533L;

	private Integer menu_id;
	private String menu_name;
	private String menu_url;
	private Integer parent_id;
	private Integer menu_order;
	private String menu_icon;
	private Integer menu_type;
	
	private FMenu parentMenu;
	
	private List<FMenu> subMenu;
	
	private boolean hasMenu = false;

	/**
	 * @return the menu_id
	 */
	public Integer getMenu_id() {
		return menu_id;
	}

	/**
	 * @param menu_id the menu_id to set
	 */
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}

	/**
	 * @return the menu_name
	 */
	public String getMenu_name() {
		return menu_name;
	}

	/**
	 * @param menu_name the menu_name to set
	 */
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	/**
	 * @return the menu_url
	 */
	public String getMenu_url() {
		return menu_url;
	}

	/**
	 * @param menu_url the menu_url to set
	 */
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	/**
	 * @return the parent_id
	 */
	public Integer getParent_id() {
		return parent_id;
	}

	/**
	 * @param parent_id the parent_id to set
	 */
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * @return the menu_order
	 */
	public Integer getMenu_order() {
		return menu_order;
	}

	/**
	 * @param menu_order the menu_order to set
	 */
	public void setMenu_order(Integer menu_order) {
		this.menu_order = menu_order;
	}

	/**
	 * @return the menu_icon
	 */
	public String getMenu_icon() {
		return menu_icon;
	}

	/**
	 * @param menu_icon the menu_icon to set
	 */
	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}

	/**
	 * @return the menu_type
	 */
	public Integer getMenu_type() {
		return menu_type;
	}

	/**
	 * @param menu_type the menu_type to set
	 */
	public void setMenu_type(Integer menu_type) {
		this.menu_type = menu_type;
	}

	/**
	 * @return the parentMenu
	 */
	public FMenu getParentMenu() {
		return parentMenu;
	}

	/**
	 * @param parentMenu the parentMenu to set
	 */
	public void setParentMenu(FMenu parentMenu) {
		this.parentMenu = parentMenu;
	}

	/**
	 * @return the subMenu
	 */
	public List<FMenu> getSubMenu() {
		return subMenu;
	}

	/**
	 * @param subMenu the subMenu to set
	 */
	public void setSubMenu(List<FMenu> subMenu) {
		this.subMenu = subMenu;
	}

	/**
	 * @return the hasMenu
	 */
	public boolean isHasMenu() {
		return hasMenu;
	}

	/**
	 * @param hasMenu the hasMenu to set
	 */
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
	
	
}
