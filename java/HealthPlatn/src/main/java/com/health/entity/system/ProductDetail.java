package com.health.entity.system;

/**
 * 产品描述实体类
 * @author XiangYu
 *
 */
public class ProductDetail {
	private static Integer id;
	private static Integer product_id;
	private static String discription;
	
	/**
	 * @return the id
	 */
	public static Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public static void setId(Integer id) {
		ProductDetail.id = id;
	}
	/**
	 * @return the product_id
	 */
	public static Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public static void setProduct_id(Integer product_id) {
		ProductDetail.product_id = product_id;
	}
	/**
	 * @return the discription
	 */
	public static String getDiscription() {
		return discription;
	}
	/**
	 * @param discription the discription to set
	 */
	public static void setDiscription(String discription) {
		ProductDetail.discription = discription;
	}
	
	
}
