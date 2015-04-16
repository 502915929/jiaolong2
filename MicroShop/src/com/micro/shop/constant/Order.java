package com.micro.shop.constant;

public enum Order {
	DESC("desc"), ASC("asc");
	private String value;

	private Order(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
