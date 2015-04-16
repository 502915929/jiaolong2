package com.micro.shop.entity;

/**
 * 动态实体类
 * 
 * @author B.B.D
 * 
 */
public class DynamicEntity {

	private boolean onlyImage;
	private String city;
	private String collect;
	private String intro;
	private int icon;
	private String name;
	private int imageUrl;

	public boolean isOnlyImage() {
		return onlyImage;
	}

	public void setOnlyImage(boolean onlyImage) {
		this.onlyImage = onlyImage;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(int imageUrl) {
		this.imageUrl = imageUrl;
	}

}
