package com.vidya_industries.services;

import java.util.List;

import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.RequestQuotationDto;
import com.vidya_industries.dtos.ResponseQuotationDto;
import com.vidya_industries.dtos.UpdateQuotationStatusDto;

public interface QuotationService {

	ApiResponse requestQuotation(Long userId, RequestQuotationDto quotationDto);

	List<ResponseQuotationDto> getRequestedQuotation();

	ApiResponse updateQuotationStatus(Long quotationId, UpdateQuotationStatusDto quotationStatus);
}
