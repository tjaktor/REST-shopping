package com.tjaktor.restshopping.core.service.dto.mapper;

import org.mapstruct.Mapper;

import com.tjaktor.restshopping.core.service.dto.user.UserDataDto;
import com.tjaktor.restshopping.security.user.ApplicationUser;

@Mapper(uses = { UserRoleMapper.class })
public interface UserDataMapper {
	
	UserDataDto map(ApplicationUser user);
}
