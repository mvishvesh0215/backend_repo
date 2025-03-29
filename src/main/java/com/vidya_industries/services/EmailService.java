package com.vidya_industries.services;

import com.vidya_industries.Entity.EmailDetails;
import com.vidya_industries.dtos.ContactUsDto;
import com.vidya_industries.dtos.RequestOrderCanellationDto;
import com.vidya_industries.dtos.RequestQuotationDto;

public interface EmailService {
    
    String sendQuotationMail(EmailDetails details,RequestQuotationDto quotationDto);

	String contactUs(ContactUsDto contactUsDto);

	String requestOrderCancellation(Long userId, RequestOrderCanellationDto requestOrderCanellationDto);
}
