package com.vidya_industries.dtos;

public class NoSuchResourceFound extends RuntimeException{
	
	public NoSuchResourceFound(String errMessage) {
		super(errMessage);
	}
}
