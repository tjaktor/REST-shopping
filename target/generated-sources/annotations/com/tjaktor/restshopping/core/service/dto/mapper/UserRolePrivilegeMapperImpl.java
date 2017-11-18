package com.tjaktor.restshopping.core.service.dto.mapper;

import com.tjaktor.restshopping.core.service.dto.user.UserPrivilegeDataDto;
import com.tjaktor.restshopping.security.user.UserPrivilege;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-11-18T12:39:23+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class UserRolePrivilegeMapperImpl implements UserRolePrivilegeMapper {

    @Override
    public UserPrivilegeDataDto map(UserPrivilege privilege) {
        if ( privilege == null ) {
            return null;
        }

        UserPrivilegeDataDto userPrivilegeDataDto = new UserPrivilegeDataDto();

        userPrivilegeDataDto.setPrivilege( privilege.getPrivilege() );

        return userPrivilegeDataDto;
    }
}
