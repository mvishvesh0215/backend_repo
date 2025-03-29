package com.vidya_industries.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestQuotationDto {
	
	private String paperType;
	private Integer layers;
	private Integer quantity;
	private String dimensions;
	private String message;
}
