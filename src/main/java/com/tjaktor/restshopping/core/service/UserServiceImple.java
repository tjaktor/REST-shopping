package com.tjaktor.restshopping.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tjaktor.restshopping.core.exception.NotFoundException;
import com.tjaktor.restshopping.core.exception.UserServiceException;
import com.tjaktor.restshopping.core.service.dto.mapper.UserDataMapper;
import com.tjaktor.restshopping.core.service.dto.user.CreateUserDto;
import com.tjaktor.restshopping.core.service.dto.user.UpdateUserDetailsDto;
import com.tjaktor.restshopping.core.service.dto.user.UserDataDto;
import com.tjaktor.restshopping.security.repository.ApplicationUserRepository;
import com.tjaktor.restshopping.security.repository.ApplicationUserRoleRepository;
import com.tjaktor.restshopping.security.user.ApplicationUser;
import com.tjaktor.restshopping.security.user.UserRole;

@Service
@Transactional
public class UserServiceImple implements UserService {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImple.class);
	
	private ApplicationUserRepository applicationUserRepository;
	private ApplicationUserRoleRepository applicationUserRoleRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImple(ApplicationUserRepository applicationUserRepository, 
			ApplicationUserRoleRepository applicationUserRoleRepository,
			PasswordEncoder passwordEncoder) {
		
		this.applicationUserRepository = applicationUserRepository;
		this.applicationUserRoleRepository = applicationUserRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@Override
	@Transactional
	public Iterable<UserDataDto> findAllUsers() {
		
		UserDataMapper userDataMapper = Mappers.getMapper(UserDataMapper.class);
		
		Iterable<ApplicationUser> users = this.applicationUserRepository.findAll();
		List<UserDataDto> userDtos = new LinkedList<>();
		
		for (ApplicationUser user : users) {
			userDtos.add(userDataMapper.map(user));
		}
		
		return userDtos;
	}
	
	
	@Override
	@Transactional
	public UserDataDto findByUsername(String username) {
		
		UserDataMapper userDataMapper = Mappers.getMapper(UserDataMapper.class);
		
		ApplicationUser user = this.applicationUserRepository.findByUsername(username);
		
		if (null == user) {
			throw new NotFoundException("user with username: '" + username + "' not found");
		}
		
		return userDataMapper.map(user);
	}

	
	@Override
	@Transactional
	public UserDataDto findByEmail(String email) {
		
		UserDataMapper userDataMapper = Mappers.getMapper(UserDataMapper.class);
		
		ApplicationUser user = this.applicationUserRepository.findByEmail(email);
		
		if (null == user) {
			throw new NotFoundException("user with email: '" + email + "' not found");
		}
		
		return userDataMapper.map(user);
	}
	
	
	@Override
	@Transactional
	public UserDataDto findById(Long userId) {
		
		UserDataMapper userDataMapper = Mappers.getMapper(UserDataMapper.class);
		
		ApplicationUser user = this.applicationUserRepository.findById(userId);
		
		if (null == user) {
			throw new NotFoundException("user with ID: " + userId + " not found");
		}
		
		return userDataMapper.map(user);
	}
	
	
	@Override
	@Transactional
	public void createNewUser(CreateUserDto newUserDto) {
		
		List<String> validationErrors = new ArrayList<>();
		
		if (isEmailRegistered(newUserDto.getEmail())) {
			validationErrors.add("email: email already registered: " + newUserDto.getEmail());
		}
		
		if ( ! newUserDto.getPassword().equals(newUserDto.getPasswordAgain())) {
			validationErrors.add("passwords: passwords are different: '" + newUserDto.getPassword() + "', '" + newUserDto.getPasswordAgain() + "'");
		}
		
		if ( ! validationErrors.isEmpty()) {
			throw new UserServiceException(validationErrors.toString());
		}
		
		ApplicationUser newUser = new ApplicationUser();
		newUser.setUsername(newUserDto.getUsername());
		newUser.setPassword(this.passwordEncoder.encode(newUserDto.getPassword()));
		newUser.setEmail(newUserDto.getEmail());
		newUser.setRoles(retrieveRolesByStringList(newUserDto.getRoles()));		
		this.applicationUserRepository.save(newUser);
	}
	
	
	@Override
	@Transactional
	public void updateUser(UpdateUserDetailsDto userDto, Long userId) {
		
		List<String> validationErrors = new ArrayList<>();
		ApplicationUser user = this.applicationUserRepository.findById(userId);
		
		if (null == user) {
			throw new NotFoundException("user update failed, user with ID: " + userId + " not found");
		}
		
		user.setUsername(userDto.getUsername());
		ApplicationUser userByEmail = this.applicationUserRepository.findByEmail(userDto.getEmail());
		
		// Ensure user email not registered to another user
		if (null != userByEmail && userByEmail.getId() != userId) {
			validationErrors.add("email: email already registered to another user: " + userDto.getEmail());
		}
		
		if ( ! validationErrors.isEmpty()) {
			throw new UserServiceException(validationErrors.toString());
		}
		
		user.setEmail(userDto.getEmail());
		this.applicationUserRepository.save(user);
	}
	
	
	@Override
	@Transactional
	public void deleteUser(Long userId) {
		
		ApplicationUser user = this.applicationUserRepository.findById(userId);
		
		if (null == user) {
			throw new NotFoundException("user delete failed, user with ID: " + userId + " not found");
		}
		
		this.applicationUserRepository.delete(user);	
	}
	
	
	/**
	 * Return true if email is already used for registration
	 * 
	 * @param email
	 * @return
	 */
	private boolean isEmailRegistered(String email) {
		
		ApplicationUser user = this.applicationUserRepository.findByEmail(email);
		
		return (null != user) ? true : false;
	}
	
	
	/**
	 * Retrieve a set of {@link UserRole}s from role repository
	 * 
	 * @param rolesList
	 * @return
	 */
	private Set<UserRole> retrieveRolesByStringList(List<String> rolesList) {
		
		if (null == rolesList || rolesList.isEmpty()) {
			return null;
		}
		
		Set<UserRole> roles = new HashSet<>();
		
		for (String role : rolesList) {
			UserRole userRole = this.applicationUserRoleRepository.findByRolename(role);
			
			if (null != userRole) {
				roles.add(userRole);
			}
		}
		
		return roles;
	}
}
