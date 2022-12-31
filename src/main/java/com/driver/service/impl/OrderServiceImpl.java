package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{


    @Autowired
    OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderEntity, orderDto);
        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        long id = orderRepository.findByOrderId(orderId).getId();
        OrderEntity orderEntity = new OrderEntity();
        order.setId(id);
        order.setOrderId(orderId);
        BeanUtils.copyProperties(order, orderEntity);
        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        orderRepository.delete(orderRepository.findByOrderId(orderId));
    }

    @Override
    public List<OrderDto> getOrders() {
        return null;
    }
}