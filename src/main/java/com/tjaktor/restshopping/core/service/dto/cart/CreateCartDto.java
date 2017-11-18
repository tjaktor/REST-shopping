package com.tjaktor.restshopping.core.service.dto.cart;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateCartDto implements Serializable {

	private static final long serialVersionUID = -3707098194597200006L;
	
	@NotEmpty(message = "cart name can not be empty")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
