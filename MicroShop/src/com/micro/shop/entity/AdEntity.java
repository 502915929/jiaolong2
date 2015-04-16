package com.micro.shop.entity;

import java.io.Serializable;

public class AdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5624402112496660402L;

	private String url;
	private String title;

	public AdEntity(String url, String title) {
		super();
		this.url = url;
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AdEntity) {
			AdEntity other = (AdEntity) o;
			if (other.getUrl().equals(this.url)
					&& other.getTitle().equals(this.title)) {
				return true;
			}
		}
		return false;
	}
}
