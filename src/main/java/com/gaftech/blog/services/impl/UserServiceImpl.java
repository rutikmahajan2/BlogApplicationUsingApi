package com.gaftech.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaftech.blog.constants.AppConstants;
import com.gaftech.blog.entities.User;
import com.gaftech.blog.exceptions.ResourceNotFoundException;
import com.gaftech.blog.payloads.UserDto;
import com.gaftech.blog.repositories.UserRepository;
import com.gaftech.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {

		logger.info("Entering dao call for save the user data");
		
		User user = this.modelMapper.map(userDto, User.class);
		
		User savedUser = this.userRepository.save(user);
		 
		logger.info("Completed dao call for save the user data");
		
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		logger.info("Entering dao call for update user data with userId : {}",userId);
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepository.save(user);
		
		logger.info("Completed dao call for update user data with userId : {}",userId);
		
		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		logger.info("Entering dao call for get user data with userId : {} ",userId);
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));
		
		logger.info("Completed dao call for get user data with userId : {} ",userId);

		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {

		logger.info("Entering dao call for get all user data");
		
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(e -> this.modelMapper.map(e, UserDto.class))
				.collect(Collectors.toList());
		
		logger.info("Completed dao call for get all user data");
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		logger.info("Entering dao call for delete user data with userId : {}",userId);
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));

		this.userRepository.delete(user);
		
		logger.info("Completed dao call for delete user data with userId : {}",userId);
		
		
	}

	

}
