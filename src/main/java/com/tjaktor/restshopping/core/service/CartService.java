package com.tjaktor.restshopping.core.service;

import org.springframework.stereotype.Service;

import com.tjaktor.restshopping.core.exception.CartServiceException;
import com.tjaktor.restshopping.core.exception.NotFoundException;
import com.tjaktor.restshopping.core.service.dto.cart.CartDataDto;
import com.tjaktor.restshopping.core.service.dto.cart.CreateCartDto;
import com.tjaktor.restshopping.core.service.dto.cart.CreateItemDto;
import com.tjaktor.restshopping.core.service.dto.cart.ItemDataDto;
import com.tjaktor.restshopping.core.service.dto.cart.UpdateCartDto;
import com.tjaktor.restshopping.core.service.dto.cart.UpdateItemDto;

@Service
public interface CartService {
	
	
	/**
	 * Get list of carts from the database.
	 * 
	 * @throws NotFoundException
	 * @return Iterable<{@link CartDataDto}>
	 */
	public Iterable<CartDataDto> listOfCarts();
		
	/**
	 * Get a cart by ID.
	 * 
	 * @param Long cartId
	 * @throws NotFoundException
	 * @return {@link CartDataDto}
	 */
	public CartDataDto findCartById(Long cartId);
		
	/**
	 * Create a new cart and insert it into the database.
	 * 
	 * @param cart
	 */
	public void createCart(CreateCartDto cart);
	
	/**
	 * Update cart's name
	 * 
	 * @param cartDto
	 * @param cartId
	 * @throws NotFoundException, CartServiceException if errors
	 */
	public void updateCart(UpdateCartDto updateDto, Long cartId);
	
	/**
	 * Delete a cart by it's ID.
	 * 
	 * @param Long cartId
	 * @throws NotFoundException
	 */
	public void deleteCart(Long cartId);
	
	/**
	 * Find all items in a cart
	 * 
	 * @param cartId
	 * @throws NotFoundException
	 * @return Iterable{@link<ItemDataDto>}
	 */
	public Iterable<ItemDataDto> findCartItemsById(Long cartId);
	
	/**
	 * Find an item by ID
	 * 	
	 * @param cartId
	 * @param itemId
	 * @throws NotFoundException
	 * @return {@link ItemDataDto}
	 */
	public ItemDataDto findItemById(Long cartId, Long itemId);
	
	/**
	 * Create a new item.
	 * 
	 * @param Long cartId - The ID of the cart the item belongs to
	 * @param Item item
	 * @throws CartServiceException if validation errors
	 */
	public void createItem(Long cartId, CreateItemDto item);
		
	/**
	 * Update an item.
	 * 
	 * @param Long cartId
	 * @param Item item
	 * @throws CartServiceException if validation errors
	 */
	public void updateItem(Long cartId, UpdateItemDto item);
		
	/**
	 * Delete an item.
	 * 
	 * @param Long cartId
	 * @param Long itemId
	 * @throws NotFoundException
	 */
	public void deleteItem(Long cartId, Long itemId);
		
	/**
	 * Change the collected status of an item.
	 * 
	 * @param Long cartId
	 * @param Long itemId
	 * @throws NotFoundException
	 */
	public void collectItem(Long cartId, Long itemId);
}
