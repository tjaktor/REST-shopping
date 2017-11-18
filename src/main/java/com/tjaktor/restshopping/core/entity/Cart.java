package com.tjaktor.restshopping.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

import com.tjaktor.restshopping.core.exception.ItemNotFoundException;

@Entity
public class Cart implements Serializable {
	
	private static final long serialVersionUID = 2584574176365848586L;

	@Id
	@GeneratedValue
	private Long cartId;
	
	@NotEmpty
	private String name;
		
	@OneToMany( mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Item> items;
	
	private int numberOfitems;

	@CreatedDate
	@Column
	private LocalDateTime createdOn;

	public Cart() {
		
	}
	
	@Override
	public String toString ( )
	{
		return "[Cart ID: " + this.cartId + ", name: " + this.name + ", created: " + this.createdOn + "]";
	}
	
	public Long getCartId() {
		return this.cartId;
	}

	public void setCartId(Long id) {
		this.cartId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
		this.numberOfitems = this.items.size();
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
	/**
	 * Return true if items-list is empty.
	 * 
	 * @return boolean
	 */
	public boolean isItemsEmpty() {
		return this.items.isEmpty();
	}
	
	/**
	 * Add an item to the cart
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		item.setCart(this);
		this.items.add(item);
		this.numberOfitems++;
	}
	
	public Item findItemById(Long itemId) {

		Item itemFound = null;
		
		for (Item item : this.items) {
			if (itemId.equals(item.getItemId())) {
				itemFound = item;
				break;
			}
		}
		
		return itemFound;
	}
	
	/**
	 * Update an item with new data.
	 * 
	 * @param newItemData
	 */
	public void updateItem(Item newItemData) {
		
		Long itemId = newItemData.getItemId();
		
		boolean itemFound = false;
		
		for (Item item : this.items) {
			if (itemId.equals(item.getItemId())) {
				itemFound = true;
				item.setItemName(newItemData.getItemName());
				item.setItemDescription(newItemData.getItemDescription());
				item.setItemAmount(newItemData.getItemAmount());
				item.setItemCollected(newItemData.getItemCollected());
				break;
			}
		}
		
		if ( ! itemFound) {
			throw new ItemNotFoundException("item not updated, requested item with ID: " + itemId + "not found");
		}
	}	
	
	/**
	 * Remove an item from the items-list.
	 * 
	 * @param itemId
	 */
	public void removeItem(Long itemId) {
		
		Boolean itemFound = false;
		
		for (Item item : this.items) {
			if (item.getItemId() == itemId) {
				this.items.remove(item);
				this.numberOfitems--;
				itemFound = true;
				break;
			}
		}
		
		if ( ! itemFound) {
			throw new ItemNotFoundException("item not found with ID: " + itemId);
		}
	}
	
	/**
	 * Mark an item collected by itemId.
	 * 	
	 * @param itemId
	 */
	public void changeCollectedStatus(Long itemId) {
		
		for (Item item : this.items ){
			if (itemId.equals(item.getItemId())) {
				item.changeCollectedStatus();				
				break;
			}
		}
	}
	
	/**
	 * Return true if all the products in the cart are collected.
	 * 
	 * @return boolean
	 */
	public boolean isAllItemsCollected() {
		long numberOfCollectedItems = this.items.stream().filter(item -> item.getItemCollected() == true).count();
				
		if (this.numberOfitems > 0 && this.numberOfitems == numberOfCollectedItems) {
			return true;
		} else {
			return false;
		}
	}
}
