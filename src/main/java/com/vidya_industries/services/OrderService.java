package com.vidya_industries.services;

import java.util.List;

import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.CurrentOrderDto;
import com.vidya_industries.dtos.OrdersAnalyticsDto;
import com.vidya_industries.dtos.RequestOrderDto;
import com.vidya_industries.dtos.ResponseOrderDto;
import com.vidya_industries.dtos.ResponseOrderWithCustomerDto;
import com.vidya_industries.dtos.UpdateOrderStatusDto;

public interface OrderService {

	List<CurrentOrderDto> currentOrder(Long userId);

	ApiResponse orderNow(Long userId, RequestOrderDto orderDto);

	List<ResponseOrderDto> allOrders(Long userId);

	List<ResponseOrderWithCustomerDto> getPendingOrders();

	List<ResponseOrderWithCustomerDto> getCompletedOrders();

	List<ResponseOrderWithCustomerDto> getCancelledOrders();
	
	ApiResponse updateOrderStatus(Long userId, UpdateOrderStatusDto updateOrderStatusDto);

	List<ResponseOrderDto> getPendingOrders(Long userId);

	List<OrdersAnalyticsDto> getOrdersAnalytics();


}
