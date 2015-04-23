package com.micro.shop.entity;

/**
 * Created by 95 on 2015/4/21.
 */
public class ProductImage {
    private Integer id;
    private String productCode;
    private String imageUrl;
    private String imageTitle;
    private String imageType;
    private Integer showIndex;
    private Integer delFlag;

    private String imageName;//图片真实名称
    private Integer isCoverImg;//是否是封面

    public ProductImage(String imageUrl,String show){
        this.imageUrl=imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Integer getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Integer showIndex) {
        this.showIndex = showIndex;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getIsCoverImg() {
        return isCoverImg;
    }

    public void setIsCoverImg(Integer isCoverImg) {
        this.isCoverImg = isCoverImg;
    }
}
