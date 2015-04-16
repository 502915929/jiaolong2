package com.micro.shop.entity;


/**
 * ShopBase entity. @author MyEclipse Persistence Tools
 */
public class ShopBase  implements
java.io.Serializable {

	// Fields

	private Integer id;
	private String shopCode;
	private String shopName;
	private String userCode;
	private String shopSmallLogo;
	private String shopLogo;
	private String slogan;
	private String shopBackground;
	private Integer shopLevel;
	private Integer provinceId;
	private String provinceName;
	private Integer cityId;
	private String cityName;
	private String district;
	private String modifyDate;
	private Integer delFlag;
	private String remark;

	// Constructors

	/** default constructor */
	public ShopBase() {
	}

	/** minimal constructor */
	public ShopBase(String shopName, String modifyDate) {
		this.shopName = shopName;
		this.modifyDate = modifyDate;
	}

	/** full constructor */
	public ShopBase(String shopCode, String shopName, String userCode,
			String shopSmallLogo, String shopLogo, String slogan,
			String shopBackground, Integer shopLevel, Integer provinceId,
			String provinceName, Integer cityId, String cityName,
			String district, String modifyDate, Integer delFlag,
			String remark) {
		this.shopCode = shopCode;
		this.shopName = shopName;
		this.userCode = userCode;
		this.shopSmallLogo = shopSmallLogo;
		this.shopLogo = shopLogo;
		this.slogan = slogan;
		this.shopBackground = shopBackground;
		this.shopLevel = shopLevel;
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.district = district;
		this.modifyDate = modifyDate;
		this.delFlag = delFlag;
		this.remark = remark;
	}



	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Property accessors
	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getShopSmallLogo() {
		return this.shopSmallLogo;
	}

	public void setShopSmallLogo(String shopSmallLogo) {
		this.shopSmallLogo = shopSmallLogo;
	}

	public String getShopLogo() {
		return this.shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getSlogan() {
		return this.slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getShopBackground() {
		return this.shopBackground;
	}

	public void setShopBackground(String shopBackground) {
		this.shopBackground = shopBackground;
	}

	public Integer getShopLevel() {
		return this.shopLevel;
	}

	public void setShopLevel(Integer shopLevel) {
		this.shopLevel = shopLevel;
	}

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
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