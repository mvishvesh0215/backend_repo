package com.vidya_industries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.RequestOrderCanellationDto;
import com.vidya_industries.dtos.RequestOrderDto;
import com.vidya_industries.dtos.RequestQuotationDto;
import com.vidya_industries.dtos.RequestUserDto;
import com.vidya_industries.services.EmailService;
import com.vidya_industries.services.OrderService;
import com.vidya_industries.services.QuotationService;
import com.vidya_industries.services.UserService;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/profile/{userId}")
	public ResponseEntity<?> getProfile(@PathVariable Long userId){
		try {
			return ResponseEntity.ok(userService.getProfile(userId));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/current-order/{userId}")
	public ResponseEntity<?> currentOrder(@PathVariable Long userId){
		try {
			return ResponseEntity.ok(orderService.currentOrder(userId));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@PostMapping("/order-now/{userId}")
	public ResponseEntity<?> orderNow(@PathVariable Long userId,@RequestBody RequestOrderDto orderDto){
		try {
			return ResponseEntity.ok(orderService.orderNow(userId,orderDto));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/all-orders/{userId}")
	public ResponseEntity<?> allOrders(@PathVariable Long userId){
		try {
			return ResponseEntity.ok(orderService.allOrders(userId));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/pending-orders/{userId}")
	public ResponseEntity<?> getPendingOrders(@PathVariable Long userId){
		try {
			return ResponseEntity.ok(orderService.getPendingOrders(userId));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@PostMapping("/request-quotation/{userId}")
	public ResponseEntity<?> requestQuotation(@PathVariable Long userId,@RequestBody RequestQuotationDto quotationDto){
		try {
			return ResponseEntity.ok(quotationService.requestQuotation(userId,quotationDto));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@PostMapping("/update-profile/{userId}")
	public ResponseEntity<?> updateProfile(@PathVariable Long userId,@RequestBody RequestUserDto requestUserDto){
		try {
			return ResponseEntity.ok(userService.updateProfile(userId,requestUserDto));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@PostMapping("/request-order-cancellation/{userId}")
	public ResponseEntity<?> requestOrderCancellation(@PathVariable Long userId,@RequestBody RequestOrderCanellationDto requestOrderCanellationDto){
		try {
			return ResponseEntity.ok(emailService.requestOrderCancellation(userId,requestOrderCanellationDto));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
}
