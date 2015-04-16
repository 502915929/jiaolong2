package com.micro.shop.entity;

/**
 * Created by 95 on 2015/4/3.
 */
public class Dynamic {

    /**
     * 验证动态内容类型1为商品动态，2为活动动态
     */
    private Integer type;


    //商品所需
    private Integer dynamicType;
    private String bak;
    private Integer delFlag;

    private String dynamicCode;
    private String productCode;
    private String shopCode;
    private String productImage;
    private String productName;
    private String shopName;
    private Double oldPrice;
    private Double salePrice;
    private String createDate;

    private String cityName;
    private String shopLogo;

    //该商品是否收藏
    private boolean hasCollect;
    //商品总收藏数
    private Integer totalGoodNum;


    //-----活动所需-------//

    private String activityCode;
    private String activityImg;




    public String getActivityCode() {
        return activityCode;
    }
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
    public String getActivityImg() {
        return activityImg;
    }
    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }


    public Integer getDynamicType() {
        return dynamicType;
    }
    public void setDynamicType(Integer dynamicType) {
        this.dynamicType = dynamicType;
    }
    public String getBak() {
        return bak;
    }
    public void setBak(String bak) {
        this.bak = bak;
    }
    public Integer getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
    public String getDynamicCode() {
        return dynamicCode;
    }
    public void setDynamicCode(String dynamicCode) {
        this.dynamicCode = dynamicCode;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getShopCode() {
        return shopCode;
    }
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public Double getOldPrice() {
        return oldPrice;
    }
    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }
    public Double getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getShopLogo() {
        return shopLogo;
    }
    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }
    public boolean isHasCollect() {
        return hasCollect;
    }
    public void setHasCollect(boolean hasCollect) {
        this.hasCollect = hasCollect;
    }
    public Integer getTotalGoodNum() {
        return totalGoodNum;
    }
    public void setTotalGoodNum(Integer totalGoodNum) {
        this.totalGoodNum = totalGoodNum;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
}
