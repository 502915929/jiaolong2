package com.micro.shop.entity;

/**
 * Created by 95 on 2015/4/18.
 */
public class Product {

    // Fields

    private Integer id;
    private String shopCode;
    private String productCode;
    private String productName;
    private String coverSmallImage;
    private String coverMiddleImage;
    private String coverBigImage;
    private Double productPrice;
    private Double salePrice;
    private Integer saleFlag;
    private Integer delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
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

    public String getCoverSmallImage() {
        return coverSmallImage;
    }

    public void setCoverSmallImage(String coverSmallImage) {
        this.coverSmallImage = coverSmallImage;
    }

    public String getCoverMiddleImage() {
        return coverMiddleImage;
    }

    public void setCoverMiddleImage(String coverMiddleImage) {
        this.coverMiddleImage = coverMiddleImage;
    }

    public String getCoverBigImage() {
        return coverBigImage;
    }

    public void setCoverBigImage(String coverBigImage) {
        this.coverBigImage = coverBigImage;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }


    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getSaleFlag() {
        return saleFlag;
    }

    public void setSaleFlag(Integer saleFlag) {
        this.saleFlag = saleFlag;
    }


    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
