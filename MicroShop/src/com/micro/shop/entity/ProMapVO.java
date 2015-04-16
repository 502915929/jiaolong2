package com.micro.shop.entity;

/**
 *
 * @author 95
 *
 */
public class ProMapVO {
	/**
	 *
	 */
	private ShopBase shopBase;
	
	/**
	 *
	 */
	private Integer proCommentNum;
	/**
	 *
	 */
	private Shop shop;
	

	public Integer getProCommentNum() {
		return proCommentNum;
	}
	public void setProCommentNum(Integer proCommentNum) {
		this.proCommentNum = proCommentNum;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public ShopBase getShopBase() {
		return shopBase;
	}
	public void setShopBase(ShopBase shopBase) {
		this.shopBase = shopBase;
	}

	
}
