package com.health.service.system;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("shoppingCartService")
public class ShoppingCartService
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public void deleteShoppingCartById(Integer id)
    throws Exception
  {
    this.dao.delete("ShoppingCartMapper.delete", id);
  }
  
  public void deleteShoppingCartByMemberId(Integer id)
    throws Exception
  {
    this.dao.delete("ShoppingCartMapper.deleteByMemberId", id);
  }
  
  public void deleteAllShoppingCarts(String[] ids)
    throws Exception
  {
    this.dao.delete("ShoppingCartMapper.deleteAll", ids);
  }
  
  /**
   * 购物车添加
   * @param pd
   * @return
   * @throws Exception
   */
  public Integer insertShoppingCart(PageData pd) throws Exception
  {
    PageData product = getShoppingCartByProductId(pd);
    if (product == null) {
      return (Integer)this.dao.save("ShoppingCartMapper.save", pd);
    }
    Integer num = Integer.parseInt(product.get("num").toString());
    Integer addnum = Integer.parseInt(pd.get("num").toString());
    pd.put("num", num + addnum);
    updateShoppingCart(pd);
    return Integer.valueOf(0);
  }
  
  public PageData getShoppingCartById(String id)
    throws Exception
  {
    return (PageData)this.dao.findForObject("ShoppingCartMapper.findById", id);
  }
  
  public List<PageData> getShoppingCartByMemberId(String id)
    throws Exception
  {
    return (List<PageData>)this.dao.findForList("ShoppingCartMapper.findByMemberId", id);
  }
  
  public PageData getShoppingCartByProductId(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("ShoppingCartMapper.findByProductId", pd);
  }
  
  public List<PageData> getDatalistPage(Page page)
    throws Exception
  {
    return (List)this.dao.findForList("ShoppingCartMapper.datalistPage", page);
  }
  
  public void updateShoppingCart(PageData pd)
    throws Exception
  {
    this.dao.update("ShoppingCartMapper.update", pd);
  }
}
