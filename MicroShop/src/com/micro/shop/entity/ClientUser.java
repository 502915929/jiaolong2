package com.micro.shop.entity;


import java.io.Serializable;

/**
 * Created by 95 on 2015/4/7.
 */
public class ClientUser implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -5624402112496660402L;

    private String  baseId;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public ClientUser(){}

    public ClientUser(String baseId){
        this.baseId=baseId;
    }
}
