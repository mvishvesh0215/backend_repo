package com.vidya_industries.dtos;

import com.vidya_industries.Entity.QuotationStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseQuotationDto {
	
	private Long id;
	private String paperType;
	private Integer layers;
	private Integer quantity;
	private String dimensions;
	private String message;
	private QuotationStatus quotationStatus;
	private ResponseCustomerDto user;
}
