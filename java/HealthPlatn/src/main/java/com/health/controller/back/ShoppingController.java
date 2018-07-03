package com.health.controller.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.ShoppingCartService;
import com.health.system.util.PageData;

/**
 * 购物相关功能
 * @author XiangYu
 *
 */
@Controller
public class ShoppingController extends BaseController{
	
	@Resource(name="shoppingCartService")
	ShoppingCartService shoppingCartService;
	
	/**
	 * 跳转到购物车界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/listShoppingCart")
	public ModelAndView toShoppingCartList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String memberNo = pd.getString("memberNo");
		String phone = pd.getString("phone");
		if((memberNo == null || memberNo.equals("")) && (phone == null || phone.equals("")) ){
			mv.addObject("shoppingCartList", null);
		}else{
			page.setPd(pd);
			List<PageData>	shoppingCartList = shoppingCartService.getDatalistPage(page);			//列出用户列表
			
			mv.addObject("shoppingCartList", shoppingCartList);
		}
		
		mv.addObject("pd", pd);
		mv.setViewName("back/shopping/shoppingCart_list");
		return mv;
	}
	
	/**
	 * 进入购物车记录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toShoppingCart")
	public ModelAndView toShoppingCart() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			String id = getRequest().getParameter("id");
			pd = shoppingCartService.getShoppingCartById(id);
			mv.addObject("pd", pd);
			mv.setViewName("back/shopping/shoppingCart_view");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}

}
