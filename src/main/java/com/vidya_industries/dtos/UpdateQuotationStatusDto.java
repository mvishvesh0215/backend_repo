package com.vidya_industries.dtos;

import com.vidya_industries.Entity.QuotationStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateQuotationStatusDto {
	
	private QuotationStatus quotationStatus;
}
