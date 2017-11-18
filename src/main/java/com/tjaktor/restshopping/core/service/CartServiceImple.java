package com.tjaktor.restshopping.core.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjaktor.restshopping.core.entity.Cart;
import com.tjaktor.restshopping.core.entity.Item;
import com.tjaktor.restshopping.core.exception.ItemNotFoundException;
import com.tjaktor.restshopping.core.exception.NotFoundException;
import com.tjaktor.restshopping.core.repository.CartRepository;
import com.tjaktor.restshopping.core.service.dto.cart.CartDataDto;
import com.tjaktor.restshopping.core.service.dto.cart.CreateCartDto;
import com.tjaktor.restshopping.core.service.dto.cart.CreateItemDto;
import com.tjaktor.restshopping.core.service.dto.cart.ItemDataDto;
import com.tjaktor.restshopping.core.service.dto.cart.UpdateCartDto;
import com.tjaktor.restshopping.core.service.dto.cart.UpdateItemDto;
import com.tjaktor.restshopping.core.service.dto.mapper.CartDataMapper;
import com.tjaktor.restshopping.core.service.dto.mapper.ItemDataMapper;

@Service
public class CartServiceImple implements CartService {
	
	private CartRepository cartRepository;
	
	@Autowired
	public CartServiceImple(CartRepository cartRepository) {
		
		this.cartRepository = cartRepository;
	}
	

	@Transactional
	@Override
	public Iterable<CartDataDto> listOfCarts() {
		
		CartDataMapper cartDataMapper = Mappers.getMapper(CartDataMapper.class);
		Iterable<Cart> carts = this.cartRepository.findAll();
		
		if (null == carts) {
			throw new NotFoundException("carts not found");
		}
		
		List<CartDataDto> cartDtos = new LinkedList<>();
		
		for (Cart cart : carts) {
			cartDtos.add(cartDataMapper.map(cart));
		}
		
		return cartDtos;
	}
	

	@Transactional
	@Override
	public CartDataDto findCartById(Long cartId) {
		
		CartDataMapper cartDataMapper = Mappers.getMapper(CartDataMapper.class);
		Cart cart = this.cartRepository.findOne(cartId);
		
		if (null == cart) {
			throw new NotFoundException("cart not found with ID: " + cartId);
		}
		
		CartDataDto cartDto = cartDataMapper.map(cart);
		return cartDto;
	}
	

	@Transactional
	@Override
	public void createCart(CreateCartDto cartDto) {
		
		Cart cart = new Cart();
		cart.setCreatedOn(LocalDateTime.now());
		cart.setName(cartDto.getName());
		this.cartRepository.save(cart);
	}

	
	@Transactional
	@Override
	public void updateCart(UpdateCartDto cartDto, Long cartId) {
		
		Cart cart = this.cartRepository.findOne(cartId);
		
		if (null == cart) {
			throw new NotFoundException("cart update failed, cart not found with ID: " + cartId);
		}
		
		cart.setName(cartDto.getName());
		this.cartRepository.save(cart);		
	}
	
	
	@Transactional
	@Override
	public void deleteCart(Long cartId) {
		
		Cart cart = this.cartRepository.findOne(cartId);
		
		if (null == cart) {
			throw new NotFoundException("cart delete failed, cart not found with ID: " + cartId);
		}
		
		this.cartRepository.delete(cart);
	}

	@Transactional
	@Override
	public Iterable<ItemDataDto> findCartItemsById(Long cartId) {
		
		Cart cart = this.cartRepository.findOne(cartId);
		
		if (null == cart) {
			throw new NotFoundException("cart delete failed, cart not found with ID: " + cartId);
		}
		
		ItemDataMapper mapper = Mappers.getMapper(ItemDataMapper.class);
		
		Iterable<ItemDataDto> items = cart.getItems().stream().map(item -> mapper.map(item)).collect(Collectors.toList());
		
		return items;
	}
	
	@Transactional
	@Override
	public ItemDataDto findItemById(Long cartId, Long itemId) {
		
		ItemDataMapper itemDataMapper = Mappers.getMapper(ItemDataMapper.class);
		Cart cart = this.cartRepository.findOne(cartId);
		
		if (null == cart) {
			throw new NotFoundException("cart not found with ID: " + cartId);
		}
		
		Item item = cart.findItemById(itemId);
		
		if (null == item) {
			throw new NotFoundException("item with ID: " + itemId + " not found in cart with ID: " + cartId);
		}
		
		ItemDataDto itemDto = itemDataMapper.map(item);
		return itemDto;
	}

	
	@Transactional
	@Override
	public void createItem(Long cartId, CreateItemDto itemDto) {

		Cart cart = this.cartRepository.findOne(cartId);
		
		if (null == cart) {
			throw new NotFoundException("cart item creation failed, cart not found with ID: " + cartId);
		}
		
		Item item = new Item();
		item.setItemName(itemDto.getName());
		item.setItemDescription(itemDto.getDescription());
		item.setItemAmount(itemDto.getAmount());
		
		cart.addItem(item);
		this.cartRepository.save(cart);
	}
	
	
	@Transactional
	@Override
	public void updateItem(Long cartId, UpdateItemDto itemDto) {
		
		Cart cart = this.cartRepository.findOne(cartId);

		if (null == cart) {
			throw new NotFoundException("cart item update failed, cart not found with ID: " + cartId);
		}
		
		Item item = cart.findItemById(itemDto.getId());
		
		if (null == item) {
			throw new NotFoundException("item with ID: " + itemDto + " not found in cart with ID: " + cartId);
		}
		
		item.setItemName(itemDto.getName());
		item.setItemDescription(itemDto.getDescription());
		item.setItemAmount(itemDto.getAmount());
		item.setItemCollected(itemDto.isCollected());
		
		// cart.updateItem(item);
		this.cartRepository.save(cart);
	}
	
	
	@Transactional
	@Override
	public void deleteItem(Long cartId, Long itemId) {

		Cart cart = this.cartRepository.findOne(cartId);

		if (null == cart) {
			throw new NotFoundException("delete cart item failed, cart not found with ID: " + cartId);
		}
		try {
			cart.removeItem(itemId);
		} catch (ItemNotFoundException ex) {
			throw new NotFoundException("delete item failed, item with ID " + itemId + " not found inside of cart " + cartId);
		}
		
		this.cartRepository.save(cart);
	}

	// Collect item DTO?
	@Transactional
	@Override
	public void collectItem(Long cartId, Long itemId) {

		Cart cart = this.cartRepository.findOne(cartId);
		
		if (null == cart) {
			throw new NotFoundException("cart item collecting failed, cart not found with ID: " + cartId);
		}
		
		cart.changeCollectedStatus(itemId);
	}
}
