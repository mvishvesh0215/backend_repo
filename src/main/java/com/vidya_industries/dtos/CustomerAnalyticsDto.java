package com.vidya_industries.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerAnalyticsDto {
	
	private String month;
	private Long customers;
}
