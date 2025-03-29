package com.vidya_industries.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthResp {

	private LoginResponse loginResponse;
	private String jwt;
}
