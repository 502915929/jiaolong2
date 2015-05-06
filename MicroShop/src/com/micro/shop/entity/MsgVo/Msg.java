package com.micro.shop.entity.MsgVo;

import com.micro.shop.entity.ClientUserBase;
import com.micro.shop.entity.DeviceClientRel;

/**
 * Created by 95 on 2015/5/5.
 */
public class Msg {
    private Boolean success;
    private String message;
    private Integer code;
    private DeviceClientRel rel;
    public ClientUserBase clientUserBase;

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DeviceClientRel getRel() {
        return rel;
    }

    public void setRel(DeviceClientRel rel) {
        this.rel = rel;
    }

    public ClientUserBase getClientUserBase() {
        return clientUserBase;
    }

    public void setClientUserBase(ClientUserBase clientUserBase) {
        this.clientUserBase = clientUserBase;
    }
}
