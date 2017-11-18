package com.tjaktor.restshopping.core.service.dto.mapper;

import com.tjaktor.restshopping.core.entity.Item;
import com.tjaktor.restshopping.core.service.dto.cart.ItemDataDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-11-18T12:39:23+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class ItemDataMapperImpl implements ItemDataMapper {

    @Override
    public ItemDataDto map(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDataDto itemDataDto = new ItemDataDto();

        itemDataDto.setName( item.getItemName() );
        itemDataDto.setDescription( item.getItemDescription() );
        itemDataDto.setAmount( item.getItemAmount() );
        itemDataDto.setCollected( item.getItemCollected() );
        itemDataDto.setId( item.getItemId() );

        return itemDataDto;
    }
}
