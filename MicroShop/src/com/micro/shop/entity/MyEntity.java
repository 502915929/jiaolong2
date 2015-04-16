package com.micro.shop.entity;

/**
 * 我的实体类
 * 
 * @author B.B.D
 * 
 */
public class MyEntity {

	private int imageUrl;
	private String title;
	private int index;

	public MyEntity(int imageUrl, String title, int index) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
		this.index = index;
	}

	public int getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(int imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
