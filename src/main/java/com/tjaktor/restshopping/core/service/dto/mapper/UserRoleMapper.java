package com.tjaktor.restshopping.core.service.dto.mapper;

import org.mapstruct.Mapper;

import com.tjaktor.restshopping.core.service.dto.user.UserRoleDataDto;
import com.tjaktor.restshopping.security.user.UserRole;

@Mapper(uses = { UserRolePrivilegeMapper.class })
public interface UserRoleMapper {

	UserRoleDataDto map(UserRole role);
}
