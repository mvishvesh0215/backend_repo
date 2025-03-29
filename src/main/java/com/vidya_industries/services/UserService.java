package com.vidya_industries.services;

import java.util.List;

import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.CustomerAnalyticsDto;
import com.vidya_industries.dtos.LoginResponse;
import com.vidya_industries.dtos.RequestUserDto;
import com.vidya_industries.dtos.ResponseUserDto;

public interface UserService {

	LoginResponse getRole(String email);

	ApiResponse registerNewUser(RequestUserDto userDto);

	ResponseUserDto getProfile(Long userId);

	ApiResponse updateProfile(Long userId, RequestUserDto requestUserDto);

	List<CustomerAnalyticsDto> getCustomerAnalytics();

}
