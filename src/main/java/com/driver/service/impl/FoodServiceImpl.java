package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = new FoodEntity();
        BeanUtils.copyProperties(food, foodEntity);
        foodRepository.save(foodEntity);
        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        FoodDto foodDto = new FoodDto();
        BeanUtils.copyProperties(foodEntity, foodDto);
        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        long id = foodRepository.findByFoodId(foodId).getId();
        FoodEntity food = FoodEntity.builder()
                .id(id)
                .foodName(foodDetails.getFoodName())
                .foodCategory(foodDetails.getFoodCategory())
                .foodPrice(foodDetails.getFoodPrice())
                .foodId(foodId)
                .build();
        food = foodRepository.save(food);
        FoodDto foodDto = new FoodDto();
        BeanUtils.copyProperties(food, foodDto);
        return foodDto;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        foodRepository.delete(foodRepository.findByFoodId(id));
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodDto> foodDtoList = new ArrayList();
        List<FoodEntity> foodList;
        foodList = (List<FoodEntity>) foodRepository.findAll();

        for(FoodEntity food : foodList){
            FoodDto foodDto = new FoodDto();
            BeanUtils.copyProperties(food, foodDto);
            foodDtoList.add(foodDto);
        }
        return foodDtoList;
    }
}