package com.tjaktor.restshopping.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tjaktor.restshopping.core.entity.Cart;

public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {

}
