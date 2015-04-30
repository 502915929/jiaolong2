package com.micro.shop.entity;

/**
 * Created by 95 on 2015/4/27.
 */
public class SearchResult {

    private String shopCode;
    private String shopName;
    private String address;
    private String shopSlogan;
    private Double range;
    private String mobile;
    private Double latitude;
    private Double longitude;
    private String shopLogo;

    private Integer hotNum;

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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getShopSlogan() {
        return shopSlogan;
    }
    public void setShopSlogan(String shopSlogan) {
        this.shopSlogan = shopSlogan;
    }
    public Double getRange() {
        return range;
    }
    public void setRange(Double range) {
        this.range = range;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
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
    public String getShopLogo() {
        return shopLogo;
    }
    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }
    public Integer getHotNum() {
        return hotNum;
    }
    public void setHotNum(Integer hotNum) {
        this.hotNum = hotNum;
    }

}
