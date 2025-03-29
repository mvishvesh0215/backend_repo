package com.vidya_industries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.AuthRequest;
import com.vidya_industries.dtos.AuthResp;
import com.vidya_industries.dtos.ContactUsDto;
import com.vidya_industries.dtos.RequestUserDto;
import com.vidya_industries.security.JwtUtils;
import com.vidya_industries.services.EmailService;
import com.vidya_industries.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/login")
	public ResponseEntity<?> userSignIn(@RequestBody @Valid AuthRequest dto) {
		UsernamePasswordAuthenticationToken
		authenticationToken = new UsernamePasswordAuthenticationToken
		(dto.getEmail(),dto.getPassword());
		Authentication authToken = 
				authenticationManager.authenticate(authenticationToken);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new AuthResp(userService.getRole(dto.getEmail()),
						jwtUtils.generateJwtToken(authToken)));
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> userSignUp(@RequestBody RequestUserDto userDto){
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userService.registerNewUser(userDto));
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PostMapping("/contact-us")
	public ResponseEntity<?> contactUs(@RequestBody ContactUsDto contactUsDto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(emailService.contactUs(contactUsDto));
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage()));
		}
	}
	
}
