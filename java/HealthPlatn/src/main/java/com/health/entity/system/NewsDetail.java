package com.health.entity.system;

/**
 * 资讯详情
 * @author XiangYu
 *
 */
public class NewsDetail {
	
	private Integer id;
	private String content;
	private Integer news_id;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the news_id
	 */
	public Integer getNews_id() {
		return news_id;
	}
	/**
	 * @param news_id the news_id to set
	 */
	public void setNews_id(Integer news_id) {
		this.news_id = news_id;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	

}
