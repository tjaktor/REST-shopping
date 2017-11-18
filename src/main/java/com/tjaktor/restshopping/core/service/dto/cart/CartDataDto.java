package com.tjaktor.restshopping.core.service.dto.cart;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class CartDataDto implements Serializable {
	
	private static final long serialVersionUID = -8322776224902015427L;
	
	private Long id;
	private String name;
	private List<ItemDataDto> items;
	private LocalDateTime createdOn;
	
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
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public List<ItemDataDto> getItems() {
		return items;
	}

	public void setItems(List<ItemDataDto> items) {
		this.items = items;
	}
	
	
}
