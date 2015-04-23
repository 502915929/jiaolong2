package com.micro.shop.entity;

import java.util.List;

/**
 * mobile店铺首页
 * @author 95
 *
 */
public class ShopIndex {


	private ShopBase shopBase;

	private boolean userIsCollect;

	private Long totalProNum;

	private Long hotNum;

	private Long totalActivityNum;

	private Long totalCollectNum;

	private List<Product> collectProList;

	private List<Product> infoList;

	private List<Product> youLoveList;



	public ShopBase getShopBase() {
		return shopBase;
	}

	public void setShopBase(ShopBase shopBase) {
		this.shopBase = shopBase;
	}

	public boolean isUserIsCollect() {
		return userIsCollect;
	}

	public void setUserIsCollect(boolean userIsCollect) {
		this.userIsCollect = userIsCollect;
	}

	public Long getTotalProNum() {
		return totalProNum;
	}

	public void setTotalProNum(Long totalProNum) {
		this.totalProNum = totalProNum;
	}

	public Long getHotNum() {
		return hotNum;
	}

	public Long getTotalCollectNum() {
		return totalCollectNum;
	}

	public void setTotalCollectNum(Long totalCollectNum) {
		this.totalCollectNum = totalCollectNum;
	}

	public void setHotNum(Long hotNum) {
		this.hotNum = hotNum;
	}

	public Long getTotalActivityNum() {
		return totalActivityNum;
	}

	public void setTotalActivityNum(Long totalActivityNum) {
		this.totalActivityNum = totalActivityNum;
	}

	public List<Product> getCollectProList() {
		return collectProList;
	}

	public void setCollectProList(List<Product> collectProList) {
		this.collectProList = collectProList;
	}

	public List<Product> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Product> infoList) {
		this.infoList = infoList;
	}

	public List<Product> getYouLoveList() {
		return youLoveList;
	}

	public void setYouLoveList(List<Product> youLoveList) {
		this.youLoveList = youLoveList;
	}
}
