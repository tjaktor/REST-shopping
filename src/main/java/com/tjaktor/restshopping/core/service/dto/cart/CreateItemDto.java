package com.tjaktor.restshopping.core.service.dto.cart;

import java.io.Serializable;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateItemDto implements Serializable {

	private static final long serialVersionUID = -8308450662127089059L;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	@Min(1)
	private int amount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
