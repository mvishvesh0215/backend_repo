package com.vidya_industries.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class ResponseUserDto {
	
	private String name;
	private String email;
	private String phone;
	private String position;
	private String company;
	private String address;
	private String password;
}
