package com.micro.shop.entity;

import java.util.List;

/**
 * Created by 95 on 2015/4/21.
 */
public class ProductDetail {

    /**
     * ��������
     */
    private ShopBase shopBase;
    private Product product;
    private ProductBase productBase;

    private List<ProductImage> imageList;
    private List<UserCommentPrudoct> commentProList;
    private List<String> labelList;
    /**
     * �û��Ƿ��ղظ���Ʒ
     */
    private Boolean userIsCollect;
    /**
     * �û��Ƿ����Ʒ����
     */
    private Boolean userGiveGood;
    /**
     * �Ƿ������Ƽ�
     */
    private Boolean proIsHot;

    private Long proCollectNum;
    private Long proInfoNum;
    private Long proGoodNum;
    private Long proCommentNum;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductBase getProductBase() {
        return productBase;
    }

    public void setProductBase(ProductBase productBase) {
        this.productBase = productBase;
    }

    public List<ProductImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductImage> imageList) {
        this.imageList = imageList;
    }

    public List<UserCommentPrudoct> getCommentProList() {
        return commentProList;
    }

    public void setCommentProList(List<UserCommentPrudoct> commentProList) {
        this.commentProList = commentProList;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public Boolean isUserIsCollect() {
        return userIsCollect;
    }

    public void setUserIsCollect(Boolean userIsCollect) {
        this.userIsCollect = userIsCollect;
    }

    public Boolean isUserGiveGood() {
        return userGiveGood;
    }

    public void setUserGiveGood(Boolean userGiveGood) {
        this.userGiveGood = userGiveGood;
    }

    public ShopBase getShopBase() {
        return shopBase;
    }

    public void setShopBase(ShopBase shopBase) {
        this.shopBase = shopBase;
    }

    public Boolean isProIsHot() {
        return proIsHot;
    }

    public void setProIsHot(Boolean proIsHot) {
        this.proIsHot = proIsHot;
    }

    public Long getProCollectNum() {
        return proCollectNum;
    }

    public void setProCollectNum(Long proCollectNum) {
        this.proCollectNum = proCollectNum;
    }

    public Long getProInfoNum() {
        return proInfoNum;
    }

    public void setProInfoNum(Long proInfoNum) {
        this.proInfoNum = proInfoNum;
    }

    public Long getProGoodNum() {
        return proGoodNum;
    }

    public void setProGoodNum(Long proGoodNum) {
        this.proGoodNum = proGoodNum;
    }

    public Long getProCommentNum() {
        return proCommentNum;
    }

    public void setProCommentNum(Long proCommentNum) {
        this.proCommentNum = proCommentNum;
    }
}
