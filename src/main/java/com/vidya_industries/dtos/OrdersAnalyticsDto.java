package com.vidya_industries.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdersAnalyticsDto {
	
	private String month;
	private Long pending;
	private Long completed;
	private Long cancelled;
}
