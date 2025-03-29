package com.vidya_industries.dtos;

import com.vidya_industries.Entity.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseOrderDto {
	
	private Long id;
	private String paperType;
	private Integer layers;
	private Integer quantity;
	private String dimensions;
	private OrderStatus orderStatus;
}
