package com.micro.shop.entity;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * DeviceClientRel entity. @author MyEclipse Persistence Tools
 */
@Table(name = "device_client_rel")
public class DeviceClientRel extends Model implements java.io.Serializable {

	// Fields

	/*@Column(name = "id")
	public String id;*/
	@Column(name = "base_id",length = 50)
	public String baseId;
	@Column(name = "user_code",length = 32)
	public String userCode;

	public DeviceClientRel() {
	}

	public DeviceClientRel(String baseId,String userCode){
		this.baseId=baseId;
		this.userCode=userCode;
	}



}