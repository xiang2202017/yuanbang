package com.health.entity.system;

import java.math.BigDecimal;

/**
 * 产品实体类
 * @author XiangYu
 *
 */
public class Product {
	private static Integer id;
	private static String name;
	private static Integer leftNum;
	private static Integer sellNum;
	private static Integer typeId;
	private static String typeName;
	private static String imgPath;
	private static Integer sellerId;
	private static String isShop;
	private static String keywords;
	private static BigDecimal price;
	
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
		Product.id = id;
	}

	/**
	 * @return the name
	 */
	public static String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public static void setName(String name) {
		Product.name = name;
	}

	/**
	 * @return the leftNum
	 */
	public static Integer getLeftNum() {
		return leftNum;
	}

	/**
	 * @param leftNum the leftNum to set
	 */
	public static void setLeftNum(Integer leftNum) {
		Product.leftNum = leftNum;
	}

	/**
	 * @return the sellNum
	 */
	public static Integer getSellNum() {
		return sellNum;
	}

	/**
	 * @param sellNum the sellNum to set
	 */
	public static void setSellNum(Integer sellNum) {
		Product.sellNum = sellNum;
	}

	/**
	 * @return the typeId
	 */
	public static Integer getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public static void setTypeId(Integer typeId) {
		Product.typeId = typeId;
	}

	/**
	 * @return the typeName
	 */
	public static String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public static void setTypeName(String typeName) {
		Product.typeName = typeName;
	}

	/**
	 * @return the imgPath
	 */
	public static String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public static void setImgPath(String imgPath) {
		Product.imgPath = imgPath;
	}

	/**
	 * @return the sellerId
	 */
	public static Integer getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId the sellerId to set
	 */
	public static void setSellerId(Integer sellerId) {
		Product.sellerId = sellerId;
	}

	/**
	 * @return the isShop
	 */
	public static String getIsShop() {
		return isShop;
	}

	/**
	 * @param isShop the isShop to set
	 */
	public static void setIsShop(String isShop) {
		Product.isShop = isShop;
	}

	/**
	 * @return the keywords
	 */
	public static String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public static void setKeywords(String keywords) {
		Product.keywords = keywords;
	}

	/**
	 * @return the price
	 */
	public static BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public static void setPrice(BigDecimal price) {
		Product.price = price;
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
		Product.discription = discription;
	}
}
