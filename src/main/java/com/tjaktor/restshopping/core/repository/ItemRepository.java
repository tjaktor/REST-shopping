package com.tjaktor.restshopping.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.tjaktor.restshopping.core.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
