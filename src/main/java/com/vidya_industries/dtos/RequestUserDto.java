package com.vidya_industries.dtos;

import lombok.Getter;

@Getter
public class RequestUserDto {
	
	private String name;
	private String email;
	private String phone;
	private String position;
	private String company;
	private String address;
	private String password;
}
