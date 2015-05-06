package com.micro.shop.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by 95 on 2015/4/24.
 */
@Table(name = "client_user_base")
public class ClientUserBase extends Model {

    @Column(name = "userCode",unique = true,notNull = true)
    public String userCode;
    @Column(name = "nickName")
    public String nickName;
    @Column(name = "userHeadImg")
    public String userHeadImg;
    @Column(name = "mobile")
    public String mobile;
    @Column(name = "qq")
    public String qq;
    @Column(name = "weibo")
    public String weibo;
    @Column(name = "weixin")
    public String weixin;
    @Column(name = "email")
    public String email;
    @Column(name = "realName")
    public String realName;
    @Column(name = "age")
    public Integer age;
    @Column(name = "delFlag")
    public Integer delFlag;
    @Column(name = "createDate")
    public String createDate;
    @Column(name = "userIsUpdate")
    public Integer userIsUpdate;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
