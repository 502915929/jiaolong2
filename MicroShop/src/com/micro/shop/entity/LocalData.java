package com.micro.shop.entity;

/**
 * Created by 95 on 2015/4/25.
 */
public class LocalData {
    //要不返回这个用户昵称，头像
    private String userNickName;
    private String userHeadImage;

    //要不返回这个店铺名称，logo
    private String shopName;
    private String shopLogo;

    private Double latitude;
    private Double longitude;
    private String shopCode;

    private String productCode;
    private String productName;
    private String productImage;
    private Double range;
    /**
     * 距现在时间
     */
    private Long upTime;
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getShopCode() {
        return shopCode;
    }
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopLogo() {
        return shopLogo;
    }
    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public String getUserNickName() {
        return userNickName;
    }
    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
    public String getUserHeadImage() {
        return userHeadImage;
    }
    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    public Long getUpTime() {
        return upTime;
    }

    public void setUpTime(Long upTime) {
        this.upTime = upTime;
    }
}
