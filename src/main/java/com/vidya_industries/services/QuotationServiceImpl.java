package com.vidya_industries.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidya_industries.Entity.EmailDetails;
import com.vidya_industries.Entity.QuotationEntity;
import com.vidya_industries.Entity.QuotationStatus;
import com.vidya_industries.Entity.UserEntity;
import com.vidya_industries.dao.QuotationDao;
import com.vidya_industries.dao.UserDao;
import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.NoSuchResourceFound;
import com.vidya_industries.dtos.RequestQuotationDto;
import com.vidya_industries.dtos.ResponseQuotationDto;
import com.vidya_industries.dtos.UpdateQuotationStatusDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class QuotationServiceImpl implements QuotationService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EmailService emailService;
	@Override
	public ApiResponse requestQuotation(Long userId, RequestQuotationDto quotationDto) {
		QuotationEntity quotationEntity = modelMapper.map(quotationDto, QuotationEntity.class);
		UserEntity userFound = userDao.findById(userId).orElseThrow(
				()-> new NoSuchResourceFound("User not found"));
		quotationEntity.setUser(userFound);
		quotationEntity.setQuotationStatus(QuotationStatus.PENDING);
		quotationDao.save(quotationEntity);
		EmailDetails details = new EmailDetails();
		details.setRecipient(userFound.getEmail());
		details.setSubject("Request for quotation");
		emailService.sendQuotationMail(details,quotationDto);
		return new ApiResponse("success");
	}
	@Override
	public List<ResponseQuotationDto> getRequestedQuotation() {
		List<QuotationEntity> list = quotationDao
				.findByQuotationStatusOrQuotationStatus
				(QuotationStatus.PENDING,QuotationStatus.COMPLETED);
		List<ResponseQuotationDto> quotationDtos = new ArrayList<>();
		for(QuotationEntity quotation:list) {
			quotationDtos.add(modelMapper.map(quotation, ResponseQuotationDto.class));
		}
		return quotationDtos;
	}
	@Override
	public ApiResponse updateQuotationStatus(Long quotationId, UpdateQuotationStatusDto quotationStatus) {
		QuotationEntity quotationFound = quotationDao.findById(quotationId).orElseThrow(
				()-> new NoSuchResourceFound("Error"));
		quotationFound.setQuotationStatus(quotationStatus.getQuotationStatus());
		quotationDao.save(quotationFound);
		return new ApiResponse("success");
	}

}
