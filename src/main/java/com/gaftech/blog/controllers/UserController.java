package com.gaftech.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaftech.blog.constants.AppConstants;
import com.gaftech.blog.payloads.ApiResponse;
import com.gaftech.blog.payloads.UserDto;
import com.gaftech.blog.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @author Rutik
	 * @param userDto
	 * @apiNote save user data
	 * @return 
	 * @since 1.0v 
	 *
	 */
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		
		log.info("Entering the request for save user data");
		
		UserDto createUserDto = this.userService.createUser(userDto);

		log.info("Completed the request for save user data");
		
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	/**
	 * @author Rutik
	 * @param userDto
	 * @param userId
	 * @apiNote update User by Id
	 * @return 
	 * @since 1.0v 
	 */
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		
		log.info("Entering the request for update user data with userId  : {}",userId);
		
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		
		log.info("Completed the request for update user data with userId  : {}",userId);
		
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}

	/**
	 * @author Rutik
	 * @param userId
	 * @apiNote delete user by id
	 * @return 
	 * @since 1.0v
	 */
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		
		log.info("Entering the request for delete user data with userId : {}",userId);
		
		this.userService.deleteUser(userId);
		
		log.info("Completed the request for delete user data with userId : {}",userId);
		
		return new ResponseEntity<>(new ApiResponse(AppConstants.DELETED, true), HttpStatus.OK);
	}

	/**
	 * @author Rutik
	 * @apiNote all user data
	 * @return 
	 * @since 1.0v 
	 */
	@GetMapping("/alluser")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		
		log.info("Entering the request for get all users data");
		
		List<UserDto> userDtos = this.userService.getAllUsers();
		
		log.info("Completed the request for get all users data");
		
		return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
	}

	/**
	 * @author Rutik
	 * @apiNote single user by id
	 * @param userId
	 * @return 
	 * @since 1.0v 
	 */
	@GetMapping("/singleuser/{userId}")
	public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId) {
		
		log.info("Entering the request for get the user data with userId : {}", userId);
		
		UserDto userDto = this.userService.getUserById(userId);
		
		log.info("Completed the request for get the user data with userId : {}", userId);
		
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
		
	}
}
