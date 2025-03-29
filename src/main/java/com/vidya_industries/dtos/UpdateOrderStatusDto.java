package com.vidya_industries.dtos;

import com.vidya_industries.Entity.OrderDetails;
import com.vidya_industries.Entity.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateOrderStatusDto {
	
	private OrderStatus orderStatus;
	private OrderDetails orderDetails;
}
