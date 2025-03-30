package com.vidya_industries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya_industries.dtos.ApiResponse;
import com.vidya_industries.dtos.RequestUserDto;
import com.vidya_industries.dtos.UpdateOrderStatusDto;
import com.vidya_industries.dtos.UpdateQuotationStatusDto;
import com.vidya_industries.services.OrderService;
import com.vidya_industries.services.QuotationService;
import com.vidya_industries.services.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("https://vidya-industries.vercel.app/")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private QuotationService quotationService;
	
	@GetMapping("/profile/{userId}")
	public ResponseEntity<?> getProfile(@PathVariable Long userId){
		try {
			System.out.println("in getProfile Controller");
			return ResponseEntity.ok(userService.getProfile(userId));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/pending-orders")
	public ResponseEntity<?> getPendingOrders(){
		try {
			return ResponseEntity.ok(orderService.getPendingOrders());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/completed-orders")
	public ResponseEntity<?> getCompletedOrders(){
		try {
			return ResponseEntity.ok(orderService.getCompletedOrders());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/cancelled-orders")
	public ResponseEntity<?> getCancelledOrders(){
		try {
			return ResponseEntity.ok(orderService.getCancelledOrders());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/requested-quotation")
	public ResponseEntity<?> getRequestedQuotation(){
		try {
			return ResponseEntity.ok(quotationService.getRequestedQuotation());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	@PutMapping("/update-quotation-status/{quotationId}")
	public ResponseEntity<?> updateQuotationStatus(@PathVariable Long quotationId,@RequestBody UpdateQuotationStatusDto quotationStatus){
		try {
			return ResponseEntity.ok(quotationService.updateQuotationStatus(quotationId,quotationStatus));
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
	
	@PostMapping("/update-order-status/{orderId}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,@RequestBody UpdateOrderStatusDto updateOrderStatusDto){
		try {
			return ResponseEntity.ok(orderService.updateOrderStatus(orderId,updateOrderStatusDto));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
	
	@GetMapping("/customers-analytics")
    public ResponseEntity<?> getCustomerAnalytics() {
		try {
			return ResponseEntity.ok(userService.getCustomerAnalytics());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
    }
	
	@GetMapping("/orders-analytics")
	public ResponseEntity<?> getOrdersAnalytics() {
		try {
			return ResponseEntity.ok(orderService.getOrdersAnalytics());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error"));
		}
	}
}
