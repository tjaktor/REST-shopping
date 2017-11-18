package com.tjaktor.restshopping.core.service;

import org.springframework.stereotype.Service;

import com.tjaktor.restshopping.core.exception.NotFoundException;
import com.tjaktor.restshopping.core.exception.UserServiceException;
import com.tjaktor.restshopping.core.service.dto.user.CreateUserDto;
import com.tjaktor.restshopping.core.service.dto.user.UpdateUserDetailsDto;
import com.tjaktor.restshopping.core.service.dto.user.UserDataDto;

@Service
public interface UserService {

	/**
	 * Find and return all users as a list
	 * 
	 * @throws NotFoundException
	 * @return
	 */
	public Iterable<UserDataDto> findAllUsers();
	
	/**
	 * Find a user by username
	 * 
	 * @param username
	 * @throws NotFoundException
	 * @return
	 */
	public UserDataDto findByUsername(String username);
	
	/**
	 * Find a user by email
	 * 
	 * @param email
	 * @throws NotFoundException
	 * @return
	 */
	public UserDataDto findByEmail(String email);
	
	/**
	 * Find a user by ID
	 * 
	 * @param id
	 * @throws NotFoundException
	 * @return
	 */
	public UserDataDto findById(Long userId);
	
	/**
	 * Create a new user
	 * 
	 * @param user
	 * @throws UserServiceException if validation errors
	 */
	public void createNewUser(CreateUserDto newUser);
	
	/**
	 * Update a user
	 * 
	 * @param user
	 * @param userId
	 * @throws NotFoundException if user not found, UserServiceException if validation errors
	 */
	public void updateUser(UpdateUserDetailsDto userDTO, Long userId);
	
	/**
	 * Delete a user by an ID
	 * 
	 * @param userId
	 * @throws NotFoundException
	 */
	public void deleteUser(Long userId);
	
}
