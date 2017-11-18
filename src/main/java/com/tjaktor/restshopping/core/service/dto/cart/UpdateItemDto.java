package com.tjaktor.restshopping.core.service.dto.cart;

import java.io.Serializable;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UpdateItemDto implements Serializable {

	private static final long serialVersionUID = 317555471122531688L;

	@JsonIgnore
	private Long id;
	
	@NotEmpty
	private String name;
	private String description;
	
	@Min(1)
	private int amount;
	private boolean collected;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public boolean isCollected() {
		return collected;
	}
	public void setCollected(boolean collected) {
		this.collected = collected;
	}
	
}
