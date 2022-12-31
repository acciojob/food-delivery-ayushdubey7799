package com.driver.ui.controller;

import java.util.List;
import java.util.UUID;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.*;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
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
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderServiceImpl orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDto orderdto = orderService.getOrderById(id);
		OrderDetailsResponse orderResponse = new OrderDetailsResponse();
		BeanUtils.copyProperties(orderdto,orderResponse);

		return orderResponse;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		String orderId = UUID.randomUUID().toString();
		orderDto.setOrderId(orderId);
		orderDto.setStatus(true);
		OrderDto responseDto = orderService.createOrder(orderDto);
		OrderDetailsResponse response = new OrderDetailsResponse();
		BeanUtils.copyProperties(responseDto,response);
		return response;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		OrderDto responseDto = orderService.updateOrderDetails(id,orderDto);
		OrderDetailsResponse response = new OrderDetailsResponse();
		BeanUtils.copyProperties(responseDto,response);
		return response;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel;
		if(orderService.getOrderById(id)==null){
			operationStatusModel = OperationStatusModel.builder()
					.operationResult(RequestOperationStatus.ERROR.toString())
					.operationName(RequestOperationName.DELETE.toString())
					.build();
		}
		else{
			orderService.deleteOrder(id);
			operationStatusModel = OperationStatusModel.builder()
					.operationResult(RequestOperationStatus.SUCCESS.toString())
					.operationName(RequestOperationName.DELETE.toString())
					.build();
		}
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		
		return null;
	}
}
