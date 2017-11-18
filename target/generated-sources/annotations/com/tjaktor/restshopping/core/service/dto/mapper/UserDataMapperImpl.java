package com.tjaktor.restshopping.core.service.dto.mapper;

import com.tjaktor.restshopping.core.service.dto.user.UserDataDto;
import com.tjaktor.restshopping.core.service.dto.user.UserRoleDataDto;
import com.tjaktor.restshopping.security.user.ApplicationUser;
import com.tjaktor.restshopping.security.user.UserRole;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-11-18T12:39:23+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class UserDataMapperImpl implements UserDataMapper {

    private final UserRoleMapper userRoleMapper = Mappers.getMapper( UserRoleMapper.class );

    @Override
    public UserDataDto map(ApplicationUser user) {
        if ( user == null ) {
            return null;
        }

        UserDataDto userDataDto = new UserDataDto();

        userDataDto.setId( user.getId() );
        userDataDto.setUsername( user.getUsername() );
        userDataDto.setEmail( user.getEmail() );
        userDataDto.setRoles( userRoleSetToUserRoleDataDtoSet( user.getRoles() ) );

        return userDataDto;
    }

    protected Set<UserRoleDataDto> userRoleSetToUserRoleDataDtoSet(Set<UserRole> set) {
        if ( set == null ) {
            return null;
        }

        Set<UserRoleDataDto> set1 = new HashSet<UserRoleDataDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UserRole userRole : set ) {
            set1.add( userRoleMapper.map( userRole ) );
        }

        return set1;
    }
}
