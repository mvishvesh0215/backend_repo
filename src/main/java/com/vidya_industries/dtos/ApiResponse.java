package com.vidya_industries.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	private LocalDateTime dateTime;
	private String message;
	
	public ApiResponse(String message) {
		super();
		this.dateTime = LocalDateTime.now();
		this.message = message;
	}
}
