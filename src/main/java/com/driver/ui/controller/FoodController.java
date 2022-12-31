package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
import org.hibernate.id.UUIDGenerator;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
    FoodServiceImpl foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
        FoodDto foodDto = foodService.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
		BeanUtils.copyProperties(foodDto,foodDetailsResponse);
		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
        FoodDto foodDto = new FoodDto();
		BeanUtils.copyProperties(foodDetails, foodDto);
		String foodId = UUID.randomUUID().toString();
		foodDto.setFoodId(foodId);
		FoodDto responseDto = foodService.createFood(foodDto);
		FoodDetailsResponse response = new FoodDetailsResponse();
		BeanUtils.copyProperties(responseDto,response);
		return response;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto = new FoodDto();
		BeanUtils.copyProperties(foodDetails, foodDto);
		FoodDto responseDto = foodService.updateFoodDetails(id,foodDto);
		FoodDetailsResponse response = new FoodDetailsResponse();
		BeanUtils.copyProperties(responseDto,response);
		return response;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel;
        if(foodService.getFoodById(id)==null){
			operationStatusModel = OperationStatusModel.builder()
					.operationResult(RequestOperationStatus.ERROR.toString())
					.operationName(RequestOperationName.DELETE.toString())
					.build();
		}
		else{
			foodService.deleteFoodItem(id);
			operationStatusModel = OperationStatusModel.builder()
					.operationResult(RequestOperationStatus.SUCCESS.toString())
					.operationName(RequestOperationName.DELETE.toString())
					.build();
		}
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
        List<FoodDetailsResponse> foodDetailsResponses = new ArrayList<>();
		List<FoodDto> foodDtoList = foodService.getFoods();
		BeanUtils.copyProperties(foodDtoList,foodDetailsResponses);
		return foodDetailsResponses;
	}
}
