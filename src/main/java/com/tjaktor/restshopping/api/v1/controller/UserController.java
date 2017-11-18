package com.tjaktor.restshopping.api.v1.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tjaktor.restshopping.api.v1.exception.ApiError;
import com.tjaktor.restshopping.core.exception.NotFoundException;
import com.tjaktor.restshopping.core.exception.UserServiceException;
import com.tjaktor.restshopping.core.service.UserService;
import com.tjaktor.restshopping.core.service.dto.user.CreateUserDto;
import com.tjaktor.restshopping.core.service.dto.user.UpdateUserDetailsDto;
import com.tjaktor.restshopping.core.service.dto.user.UserDataDto;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Find all users
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listAllUsers() {
		
		Iterable<UserDataDto> users = this.userService.findAllUsers();
		
		return new ResponseEntity<Iterable<UserDataDto>>(users, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Find a user by email
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/find-user", method = RequestMethod.GET)
	public ResponseEntity<?> findUserByEmail(@RequestParam("email") String email) {
		
		UserDataDto user = this.userService.findByEmail(email);
		
		return new ResponseEntity<UserDataDto>(user, new HttpHeaders(), HttpStatus.OK);		
	}
	
	/**
	 * Find a user by ID
	 * 
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> findUser(@PathVariable("userId") Long userId) throws NotFoundException {
		
		UserDataDto user = this.userService.findById(userId);
		
		return new ResponseEntity<UserDataDto>(user, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Create a new user
	 * 
	 * @param newUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createNewUser(@RequestBody @Valid CreateUserDto newUser) {
		
		try {
			this.userService.createNewUser(newUser);
		} catch (UserServiceException ex) {
			
			ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage());
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
			
		}
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
	}
	
	/**
	 * Update a user
	 * 
	 * @param userDto
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserDetailsDto userDto, @PathVariable("userId") Long userId) {
		
		try {
			this.userService.updateUser(userDto, userId);
		} catch (UserServiceException ex) {
			
			ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage());
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
		}
				
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Delete a user
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
		
		try {
			this.userService.deleteUser(userId);
		} catch (NotFoundException ex) {
			
			String error = "user delete failed, user not found with id: " + userId;
			logger.warn(error);
			
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT, error);
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
	}
}
