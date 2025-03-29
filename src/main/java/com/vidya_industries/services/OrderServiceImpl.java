package com.vidya_industries.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidya_industries.Entity.OrderDetails;
import com.vidya_industries.Entity.OrderEntity;
import com.vidya_industries.Entity.OrderStatus;
import com.vidya_industries.Entity.UserEntity;
import com.vidya_industries.dao.OrderDao;
import com.vidya_industries.dao.UserDao;
import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.CurrentOrderDto;
import com.vidya_industries.dtos.NoSuchResourceFound;
import com.vidya_industries.dtos.OrdersAnalyticsDto;
import com.vidya_industries.dtos.RequestOrderDto;
import com.vidya_industries.dtos.ResponseOrderDto;
import com.vidya_industries.dtos.ResponseOrderWithCustomerDto;
import com.vidya_industries.dtos.UpdateOrderStatusDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ModelMapper modelMapper;
	
	//customer service
	@Override
	public List<CurrentOrderDto> currentOrder(Long userId) {
		UserEntity userFound = userDao.findById(userId).orElseThrow(
				()-> new NoSuchResourceFound("Error in finding user"));
		List<OrderEntity> orderFound = orderDao.findByUserAndOrderDetails(
				userFound,OrderDetails.CURRENT);
		List<CurrentOrderDto> currentOrders = new ArrayList<>();
		for(OrderEntity entity:orderFound) {			
			currentOrders.add(modelMapper.map(entity, CurrentOrderDto.class));
		}
		return currentOrders;
	}
	//customer service
	@Override
	public ApiResponse orderNow(Long userId, RequestOrderDto orderDto) {
		OrderEntity newOrder = modelMapper.map(orderDto, OrderEntity.class);
		newOrder.setUser(userDao.findById(userId).
				orElseThrow(()-> new NoSuchResourceFound("Error in finding user")));
		newOrder.setOrderStatus(OrderStatus.ORDERED);
		newOrder.setOrderDetails(OrderDetails.CURRENT);
		orderDao.save(newOrder);
		return new ApiResponse("success");
	}
	//customer service
	@Override
	public List<ResponseOrderDto> allOrders(Long userId) {
		UserEntity userFound = userDao
				.findById(userId).orElseThrow(
						()-> new NoSuchResourceFound("No User found"));
		List<OrderEntity> ordersFound = orderDao.findByUser(userFound);
		List<ResponseOrderDto> ordersDto = new ArrayList<>();
		for(OrderEntity orders:ordersFound) {
			ordersDto.add(modelMapper.map(orders, ResponseOrderDto.class));
		}
		return ordersDto;
	}
	
	//admin service
	@Override
	public List<ResponseOrderWithCustomerDto> getPendingOrders() {
		List<OrderEntity> ordersFound = orderDao.findByOrderStatusNotIn(Arrays.asList(OrderStatus.DELIVERED, OrderStatus.CANCELLED));
		List<ResponseOrderWithCustomerDto> ordersDto = new ArrayList<>(); 
		for(OrderEntity orders:ordersFound) {
			ordersDto.add(modelMapper.map(orders, ResponseOrderWithCustomerDto.class));
		}
		return ordersDto;
	}
	//admin service
	@Override
	public List<ResponseOrderWithCustomerDto> getCompletedOrders() {
		List<OrderEntity> ordersFound = orderDao.findByOrderStatus(OrderStatus.DELIVERED);
		List<ResponseOrderWithCustomerDto> ordersDto = new ArrayList<>(); 
		for(OrderEntity orders:ordersFound) {
			ordersDto.add(modelMapper.map(orders, ResponseOrderWithCustomerDto.class));
		}
		return ordersDto;
	}
	//admin-service
	@Override
	public ApiResponse updateOrderStatus(Long orderId, UpdateOrderStatusDto updateOrderStatusDto) {
		OrderEntity orderFound = orderDao.findById(orderId).orElseThrow(
				()-> new NoSuchResourceFound("Order not found"));
		orderFound.setOrderStatus(updateOrderStatusDto.getOrderStatus());
		orderFound.setOrderDetails(updateOrderStatusDto.getOrderDetails());
		orderDao.save(orderFound);
		return new ApiResponse("success");
	}
	//admin-service
	@Override
	public List<ResponseOrderWithCustomerDto> getCancelledOrders() {
		List<OrderEntity> ordersFound = orderDao.findByOrderStatus(OrderStatus.CANCELLED);
		List<ResponseOrderWithCustomerDto> ordersDto = new ArrayList<>(); 
		for(OrderEntity orders:ordersFound) {
			ordersDto.add(modelMapper.map(orders, ResponseOrderWithCustomerDto.class));
		}
		return ordersDto;
	}
	@Override
	public List<ResponseOrderDto> getPendingOrders(Long userId) {
		UserEntity userFound = userDao.findById(userId)
				.orElseThrow(()->new NoSuchResourceFound("Error"));
		List<OrderEntity> ordersFound = orderDao.findByUserAndOrderStatusNotIn(userFound,Arrays.asList(OrderStatus.DELIVERED, OrderStatus.CANCELLED));
		List<ResponseOrderDto> pendingOrders = new ArrayList<>();
		for(OrderEntity entity:ordersFound) {
			pendingOrders.add(modelMapper.map(entity, ResponseOrderDto.class));
		}
		return pendingOrders;
	}
	@Override
	public List<OrdersAnalyticsDto> getOrdersAnalytics() {
	    List<OrderEntity> orderFound = orderDao.findAll(); // Assuming you have an appropriate method in your DAO
	    List<OrdersAnalyticsDto> analyticsDtos = new ArrayList<>();
	    Map<String, OrdersAnalyticsDto> map = new HashMap<>();
	    
	    for (OrderEntity order : orderFound) {
	        String month = order.getCreatedOn().getMonth().toString();
	        OrdersAnalyticsDto dto = map.getOrDefault(month, new OrdersAnalyticsDto());
	        
	        dto.setMonth(month);
	        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
	            dto.setCancelled(dto.getCancelled() == null ? 1 : dto.getCancelled() + 1);
	        } else if (order.getOrderStatus() == OrderStatus.DELIVERED) {
	            dto.setCompleted(dto.getCompleted() == null ? 1 : dto.getCompleted() + 1);
	        } else {
	            dto.setPending(dto.getPending() == null ? 1 : dto.getPending() + 1);
	        }
	        
	        map.put(month, dto);
	    }
	    
	    analyticsDtos.addAll(map.values());
	    return analyticsDtos;
	}

}
