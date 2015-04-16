package com.micro.shop.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Created by 95 on 2015/4/7.
 */
@Table(name = "client_user")
public class ClientUser {

    @Id
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
