package com.tjaktor.restshopping.core.service.dto.mapper;

import org.mapstruct.Mapper;

import com.tjaktor.restshopping.core.service.dto.user.UserPrivilegeDataDto;
import com.tjaktor.restshopping.security.user.UserPrivilege;

@Mapper
public interface UserRolePrivilegeMapper {

	UserPrivilegeDataDto map(UserPrivilege privilege);
}
