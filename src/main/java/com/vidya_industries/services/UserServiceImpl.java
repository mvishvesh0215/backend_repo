package com.vidya_industries.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vidya_industries.Entity.Role;
import com.vidya_industries.Entity.UserEntity;
import com.vidya_industries.dao.UserDao;
import com.vidya_industries.dtos.ApiException;
import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.CustomerAnalyticsDto;
import com.vidya_industries.dtos.LoginResponse;
import com.vidya_industries.dtos.NoSuchResourceFound;
import com.vidya_industries.dtos.RequestUserDto;
import com.vidya_industries.dtos.ResponseUserDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public LoginResponse getRole(String email) {
		UserEntity userFound = userDao.findByEmail(email).orElseThrow(()-> new NoSuchResourceFound("Invalid Email"));
		return new LoginResponse(userFound.getId(), userFound.getRole().toString(),"success");
	}

	@Override
	public ApiResponse registerNewUser(RequestUserDto userDto) {
		if (userDao.existsByEmail(userDto.getEmail()))
			throw new ApiException("User already exists");
		UserEntity user = modelMapper.map(userDto, UserEntity.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.ROLE_CUSTOMER);
		userDao.save(user);
		return new ApiResponse("success");
	}

	@Override
	public ResponseUserDto getProfile(Long userId) {
		UserEntity userFound = userDao.findById(userId).orElseThrow(()-> 
		new NoSuchResourceFound("Error in finding user details"));
		ResponseUserDto responseUserDto = modelMapper.map(userFound, ResponseUserDto.class);
		return responseUserDto;
	}

	@Override
	public ApiResponse updateProfile(Long userId, RequestUserDto requestUserDto) {
		UserEntity userFound = userDao.findById(userId).orElseThrow(()-> new NoSuchResourceFound("Error in finding user details"));
		userFound.setName(requestUserDto.getName());
		userFound.setEmail(requestUserDto.getEmail());
		userFound.setPhone(requestUserDto.getPhone());
		userFound.setPosition(requestUserDto.getPosition());
		userFound.setCompany(requestUserDto.getCompany());
		userFound.setAddress(requestUserDto.getAddress());
		if(userFound.getPassword().equals(requestUserDto.getPassword())) {
			userDao.save(userFound);
			return new ApiResponse("success");
		}
		else if(passwordEncoder.matches(requestUserDto.getPassword(), userFound.getPassword())) {
			userDao.save(userFound);
			return new ApiResponse("success");
		}
		else {			
			userFound.setPassword(passwordEncoder.encode(requestUserDto.getPassword()));
			userDao.save(userFound);
			return new ApiResponse("success");
		}
	}

	@Override
	public List<CustomerAnalyticsDto> getCustomerAnalytics() {
	    List<UserEntity> userFound = userDao.findByRole(Role.ROLE_CUSTOMER);
	    List<CustomerAnalyticsDto> analyticsDtos = new ArrayList<>();
	    Map<String, Long> map = new HashMap<>();
	    
	    for (UserEntity entity : userFound) {
	        String month = entity.getCreatedOn().getMonth().toString();
	        map.put(month, map.getOrDefault(month, 0l) + 1);
	    }
	    
	    map.forEach((month, count) -> {
	        CustomerAnalyticsDto dto = new CustomerAnalyticsDto();
	        dto.setMonth(month);
	        dto.setCustomers(count);
	        analyticsDtos.add(dto);
	    });
	    
	    return analyticsDtos;
	}

}
