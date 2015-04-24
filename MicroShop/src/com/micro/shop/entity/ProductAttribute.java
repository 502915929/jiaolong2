package com.micro.shop.entity;

/**
 * Created by 95 on 2015/4/24.
 */
public class ProductAttribute {

    private Integer id;
    private String productCode;
    private String attributeName;
    private String attributeContent;

    private Integer attributeShowOrder;
    private Integer delFlag;

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

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeContent() {
        return attributeContent;
    }

    public void setAttributeContent(String attributeContent) {
        this.attributeContent = attributeContent;
    }

    public Integer getAttributeShowOrder() {
        return attributeShowOrder;
    }

    public void setAttributeShowOrder(Integer attributeShowOrder) {
        this.attributeShowOrder = attributeShowOrder;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
