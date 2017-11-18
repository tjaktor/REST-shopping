package com.tjaktor.restshopping.core.service.dto.cart;

import java.io.Serializable;

public class UpdateCartDto implements Serializable {

	private static final long serialVersionUID = 5605730235553669342L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
