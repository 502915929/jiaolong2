package com.micro.shop.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.sql.Timestamp;

/**
 * Device entity. @author MyEclipse Persistence Tools
 */
@Table(name = "device")
public class Device extends Model implements java.io.Serializable {

	// Fields

	@Column(name = "base_id",unique = true,notNull = true)
	public String baseId;

	@Column(name = "create_date",notNull = true)
	public Timestamp createDate;

	public Device(){}

	public Device(String baseId, Timestamp createDate) {
		this.baseId = baseId;
		this.createDate = createDate;
	}

}