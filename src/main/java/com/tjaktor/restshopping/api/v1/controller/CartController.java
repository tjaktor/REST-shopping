package com.tjaktor.restshopping.api.v1.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tjaktor.restshopping.api.v1.exception.ApiError;
import com.tjaktor.restshopping.core.exception.CartServiceException;
import com.tjaktor.restshopping.core.exception.NotFoundException;
import com.tjaktor.restshopping.core.service.CartService;
import com.tjaktor.restshopping.core.service.dto.cart.CartDataDto;
import com.tjaktor.restshopping.core.service.dto.cart.CreateCartDto;
import com.tjaktor.restshopping.core.service.dto.cart.CreateItemDto;
import com.tjaktor.restshopping.core.service.dto.cart.ItemDataDto;
import com.tjaktor.restshopping.core.service.dto.cart.UpdateCartDto;
import com.tjaktor.restshopping.core.service.dto.cart.UpdateItemDto;

@RestController
@RequestMapping("/api/v1")
public class CartController {
	
	Logger logger = LoggerFactory.getLogger(CartController.class);
	
	private CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	/**
	 * Get all the carts
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carts", method = RequestMethod.GET)
	public ResponseEntity<?> getCarts() {
		
		Iterable<CartDataDto> carts = this.cartService.listOfCarts();
		
		return new ResponseEntity<Iterable<CartDataDto>>(carts, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Create a new cart
	 * 
	 * @param cartDto
	 * @return
	 */
	@RequestMapping(value = "/carts", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<?> postCart(@RequestBody @Valid CreateCartDto cartDto) {
		
		this.cartService.createCart(cartDto);
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
	}
		
	/**
	 * Get a cart by ID
	 * 
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value = "/carts/{cartId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCartById(@PathVariable("cartId") Long cartId) {
		
		CartDataDto cart = this.cartService.findCartById(cartId);
		
		return new ResponseEntity<CartDataDto>(cart, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Update a cart with ID
	 * 
	 * @param cartId
	 * @param updateDto
	 * @return
	 */
	@RequestMapping(value = "/carts/{cartId}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<?> updateCart(@PathVariable("cartId") Long cartId, @RequestBody @Valid UpdateCartDto updateDto) {
		
		try {
			this.cartService.updateCart(updateDto, cartId);
		} catch (CartServiceException ex) {
			
			String error = "cart update failed: " + ex.getMessage();
			logger.info(error);
			
			ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, error);
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
		}
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Delete a cart by ID
	 * 
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value = "/carts/{cartId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<?> deleteCartById(@PathVariable("cartId") Long cartId) {
				
		try {
			this.cartService.deleteCart(cartId);
		} catch (NotFoundException ex) {
			
			String error = ex.getMessage();
			logger.info(ex.getMessage());
			
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT, error);
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
		}
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Get a cart's items
	 * 
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value = "/carts/{cartId}/items", method = RequestMethod.GET)
	public ResponseEntity<?> getCartItemsById(@PathVariable("cartId") Long cartId) {
		
		Iterable<ItemDataDto> items = this.cartService.findCartItemsById(cartId);
		
		return new ResponseEntity<Iterable<ItemDataDto>>(items, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Create a new item into a cart
	 * 
	 * @param cartId
	 * @param itemDto
	 * @return
	 */
	@RequestMapping(value ="/carts/{cartId}/items", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<?> createItem(@PathVariable("cartId") Long cartId, @RequestBody @Valid CreateItemDto itemDto) {

		this.cartService.createItem(cartId, itemDto);
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
	}
	
	/**
	 * Get an item by ID
	 * 
	 * @param cartId
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value ="/carts/{cartId}/items/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<?> getItemById(@PathVariable("cartId") Long cartId, @PathVariable("itemId") Long itemId) {
		
		ItemDataDto item = this.cartService.findItemById(cartId, itemId);
		
		return new ResponseEntity<ItemDataDto>(item, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	/**
	 * Update a cart's item
	 * 
	 * @param cartId
	 * @param itemId
	 * @param item
	 * @return
	 */
	@RequestMapping(value ="/carts/{cartId}/items/{itemId}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<?> updateItem(@PathVariable("cartId") Long cartId, @PathVariable("itemId") Long itemId, @RequestBody @Valid UpdateItemDto item) {
		
		try {
			item.setId(itemId);
			this.cartService.updateItem(cartId, item);
		} catch (CartServiceException ex) {
			
			String error = "cart update failed: " + ex.getMessage();
			logger.info(error);
			
			ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, error);
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
		}
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Delete an item from a cart
	 * 
	 * @param cartId
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/carts/{cartId}/items/{itemId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<?> deleteItemById(@PathVariable("cartId") Long cartId, @PathVariable("itemId") Long itemId) {
		
		try {
			this.cartService.deleteItem(cartId, itemId);
		} catch (NotFoundException ex) {
			
			String error = ex.getMessage();
			logger.info(error);
			
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT, error);
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
		}
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
	}
}
