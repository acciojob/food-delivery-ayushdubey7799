package com.driver.ui.controller;

import java.util.List;
import java.util.UUID;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.*;
import com.driver.service.UserService;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;
	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
        UserDto userdto = userService.getUserByUserId(id);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userdto,userResponse);

		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		UserDto responseDto = userService.createUser(userDto);
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(responseDto,response);
		return response;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto responseDto = userService.updateUser(id,userDto);
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(responseDto,response);
		return response;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel;
		if(userService.getUserByUserId(id)==null){
			operationStatusModel = OperationStatusModel.builder()
					.operationResult(RequestOperationStatus.ERROR.toString())
					.operationName(RequestOperationName.DELETE.toString())
					.build();
		}
		else{
			userService.deleteUser(id);
			operationStatusModel = OperationStatusModel.builder()
					.operationResult(RequestOperationStatus.SUCCESS.toString())
					.operationName(RequestOperationName.DELETE.toString())
					.build();
		}
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){

		return null;
	}
	
}
