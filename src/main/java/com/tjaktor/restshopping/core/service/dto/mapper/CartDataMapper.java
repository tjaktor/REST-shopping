package com.tjaktor.restshopping.core.service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.tjaktor.restshopping.core.entity.Cart;
import com.tjaktor.restshopping.core.service.dto.cart.CartDataDto;

@Mapper(uses = { ItemDataMapper.class })
public interface CartDataMapper {
	
	@Mappings({
		@Mapping(target = "id", source = "cart.cartId")
	})
	CartDataDto map(Cart cart);
}
