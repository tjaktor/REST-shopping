package com.tjaktor.restshopping.core.service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.tjaktor.restshopping.core.entity.Item;
import com.tjaktor.restshopping.core.service.dto.cart.ItemDataDto;

@Mapper
public interface ItemDataMapper {

	@Mappings({
		@Mapping(target = "id", source = "item.itemId"),
		@Mapping(target = "name", source = "item.itemName"),
		@Mapping(target = "description", source = "item.itemDescription"),
		@Mapping(target = "amount", source = "item.itemAmount"),
		@Mapping(target = "collected", source = "item.itemCollected")
	})
	ItemDataDto map(Item item);
}
