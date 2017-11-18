package com.tjaktor.restshopping.core.service.dto.mapper;

import com.tjaktor.restshopping.core.entity.Cart;
import com.tjaktor.restshopping.core.entity.Item;
import com.tjaktor.restshopping.core.service.dto.cart.CartDataDto;
import com.tjaktor.restshopping.core.service.dto.cart.ItemDataDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-11-18T12:39:23+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class CartDataMapperImpl implements CartDataMapper {

    private final ItemDataMapper itemDataMapper = Mappers.getMapper( ItemDataMapper.class );

    @Override
    public CartDataDto map(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDataDto cartDataDto = new CartDataDto();

        cartDataDto.setId( cart.getCartId() );
        cartDataDto.setName( cart.getName() );
        cartDataDto.setCreatedOn( cart.getCreatedOn() );
        cartDataDto.setItems( itemListToItemDataDtoList( cart.getItems() ) );

        return cartDataDto;
    }

    protected List<ItemDataDto> itemListToItemDataDtoList(List<Item> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemDataDto> list1 = new ArrayList<ItemDataDto>( list.size() );
        for ( Item item : list ) {
            list1.add( itemDataMapper.map( item ) );
        }

        return list1;
    }
}
