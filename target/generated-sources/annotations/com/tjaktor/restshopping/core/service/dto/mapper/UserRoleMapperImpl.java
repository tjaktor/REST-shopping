package com.tjaktor.restshopping.core.service.dto.mapper;

import com.tjaktor.restshopping.core.service.dto.user.UserPrivilegeDataDto;
import com.tjaktor.restshopping.core.service.dto.user.UserRoleDataDto;
import com.tjaktor.restshopping.security.user.UserPrivilege;
import com.tjaktor.restshopping.security.user.UserRole;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-11-18T13:14:38+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class UserRoleMapperImpl implements UserRoleMapper {

    private final UserRolePrivilegeMapper userRolePrivilegeMapper = Mappers.getMapper( UserRolePrivilegeMapper.class );

    @Override
    public UserRoleDataDto map(UserRole role) {
        if ( role == null ) {
            return null;
        }

        UserRoleDataDto userRoleDataDto = new UserRoleDataDto();

        userRoleDataDto.setRolename( role.getRolename() );
        userRoleDataDto.setPrivileges( userPrivilegeSetToUserPrivilegeDataDtoSet( role.getPrivileges() ) );

        return userRoleDataDto;
    }

    protected Set<UserPrivilegeDataDto> userPrivilegeSetToUserPrivilegeDataDtoSet(Set<UserPrivilege> set) {
        if ( set == null ) {
            return null;
        }

        Set<UserPrivilegeDataDto> set1 = new HashSet<UserPrivilegeDataDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UserPrivilege userPrivilege : set ) {
            set1.add( userRolePrivilegeMapper.map( userPrivilege ) );
        }

        return set1;
    }
}
