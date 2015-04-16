package com.micro.shop.entity;

/**
 * Shop entity. @author MyEclipse Persistence Tools
 */

public class Shop  implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private String shopCode;
	private String userCode;
	private Long baiduSendId;
	private String title;
	private String address;
	private String tags;
	private Double latitude;
	private Double longitude;
	private String coordType;
	private Integer geotableId;
	private String createDate;
	private Integer delFlag;
	private String remark;

	// Constructors

	/** default constructor */
	public Shop() {
	}

	/** minimal constructor */
	public Shop(String shopCode, String createDate) {
		this.shopCode = shopCode;
		this.createDate = createDate;
	}

	/** full constructor */
	public Shop(String shopCode, String userCode, Long baiduSendId,
			String title, String address, String tags, Double latitude,
			Double longitude, String coordType, Integer geotableId,
				String createDate, Integer delFlag, String remark) {
		this.shopCode = shopCode;
		this.userCode = userCode;
		this.baiduSendId = baiduSendId;
		this.title = title;
		this.address = address;
		this.tags = tags;
		this.latitude = latitude;
		this.longitude = longitude;
		this.coordType = coordType;
		this.geotableId = geotableId;
		this.createDate = createDate;
		this.delFlag = delFlag;
		this.remark = remark;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}


	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public Long getBaiduSendId() {
		return this.baiduSendId;
	}

	public void setBaiduSendId(Long baiduSendId) {
		this.baiduSendId = baiduSendId;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}


	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public String getCoordType() {
		return this.coordType;
	}

	public void setCoordType(String coordType) {
		this.coordType = coordType;
	}


	public Integer getGeotableId() {
		return this.geotableId;
	}

	public void setGeotableId(Integer geotableId) {
		this.geotableId = geotableId;
	}


	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}