package com.vidya_industries.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.vidya_industries.Entity.EmailDetails;
import com.vidya_industries.Entity.OrderEntity;
import com.vidya_industries.Entity.Role;
import com.vidya_industries.Entity.UserEntity;
import com.vidya_industries.dao.OrderDao;
import com.vidya_industries.dao.UserDao;
import com.vidya_industries.dtos.ContactUsDto;
import com.vidya_industries.dtos.NoSuchResourceFound;
import com.vidya_industries.dtos.RequestOrderCanellationDto;
import com.vidya_industries.dtos.RequestQuotationDto;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
	
	@Autowired 
	private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") 
    private String sender;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private OrderDao orderDao;
	
	@Override
	public String sendQuotationMail(EmailDetails details,RequestQuotationDto quotationDto) {
		String message = quotationRequestToCustomer(details,quotationDto);
		List<UserEntity> admins = userDao.findByRole(Role.ROLE_ADMIN);
		quotationRequestToAdmin(quotationDto,admins);
		return message;
	}
	
	@Override
	public String contactUs(ContactUsDto contactUsDto) {
		String message = contactUsToCustomer(contactUsDto);
		List<UserEntity> admins = userDao.findByRole(Role.ROLE_ADMIN);
		contactUsToAdmin(contactUsDto,admins);
		return message;
	}

	public String contactUsToCustomer(ContactUsDto contactUsDto) {
		try {
	        // Creating a MimeMessage
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

	        // Enable multipart mode to handle HTML content
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

	        // Setting up necessary details
	        helper.setFrom(sender);
	        helper.setTo(contactUsDto.getEmail());
	        helper.setSubject("Contact Request");

	        // HTML content for the email
	        String htmlContent = "<html>" +
	        	    "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>" +
	        	    "<div style='max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; " +
	        	    "box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); animation: fadeIn 1.5s ease-in-out;'>" +
	        	    "<style>" +
	        	        "@keyframes fadeIn {" +
	        	        "    from { opacity: 0; transform: translateY(-20px); }" +
	        	        "    to { opacity: 1; transform: translateY(0); }" +
	        	        "}" +
	        	    "</style>" +
	        	    "<h2 style='color: #0056b3; text-align: center;'>Thank You for Reaching Out!</h2>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>Dear <strong>" + contactUsDto.getName() + "</strong>,</p>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>We greatly appreciate your interest in our services. Our team values each request and strives to provide you with the best possible assistance. Rest assured, your details have been received and are being processed. One of our representatives will get in touch with you shortly with all the required information.</p>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>Here are the details you provided for reference:</p>" +
	        	    "<div style='margin: 20px 0; padding: 10px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
	        	        "<p><strong>Name:</strong> " + contactUsDto.getName() + "</p>" +
	        	        "<p><strong>Company:</strong> " + contactUsDto.getCompany() + "</p>" +
	        	        "<p><strong>Email:</strong> " + contactUsDto.getEmail() + "</p>" +
	        	        "<p><strong>Phone:</strong> " + contactUsDto.getPhone() + "</p>" +
	        	        "<p><strong>Message:</strong> " + contactUsDto.getMessage() + "</p>" +
	        	    "</div>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>If there’s anything else you’d like to add or inquire about, feel free to reply to this email or contact us directly. We are here to help!</p>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>Thank you for choosing our services. We look forward to serving you!</p>" +
	        	    "<p style='margin-top: 20px; color: #555; font-size: 12px;'>Best Regards,<br>The Customer Service Team</p>" +
	        	    "</div>" +
	        	    "</body>" +
	        	    "</html>";

	        // Set the HTML content
	        helper.setText(htmlContent, true);

	        // Send the email
	        javaMailSender.send(mimeMessage);

	        return "success";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error while Sending Contact Mail";
	    }
	}

	private String contactUsToAdmin(ContactUsDto contactUsDto,List<UserEntity> admins) {
		try {

	        for(UserEntity admin:admins) {
	        	// Creating a MimeMessage
	        	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        	
	        	// Enable multipart mode to handle HTML content
	        	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        	// Setting up necessary details
	        	helper.setFrom(sender);	        	
	        	helper.setTo(admin.getEmail());
		        helper.setSubject("Contact Request");

		        // HTML content for the email
		        String htmlContent = "<html>" +
					    "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>" +
					    "<div style='max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; " +
					    "box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);'>" +
					    "<h2 style='color: #e63946; text-align: center;'>New Customer Query</h2>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>Hello "+ admin.getName() +",</p>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>A new customer query has been received. Below are the details submitted by the user:</p>" +
					    "<div style='margin: 20px 0; padding: 10px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
					        "<p><strong>Name:</strong> " + contactUsDto.getName() + "</p>" +
					        "<p><strong>Company:</strong> " + contactUsDto.getCompany() + "</p>" +
					        "<p><strong>Email:</strong> " + contactUsDto.getEmail() + "</p>" +
					        "<p><strong>Phone:</strong> " + contactUsDto.getPhone() + "</p>" +
					        "<p><strong>Message:</strong> " + contactUsDto.getMessage() + "</p>" +
					    "</div>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>Please review the query and take the necessary actions at your earliest convenience.</p>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>Thank you for your prompt attention to this matter.</p>" +
					    "<p style='margin-top: 20px; color: #555; font-size: 12px;'>Best Regards,<br>Automated Notification System</p>" +
					    "</div>" +
					    "</body>" +
					    "</html>";

		        // Set the HTML content
		        helper.setText(htmlContent, true);

		        // Send the email
		        javaMailSender.send(mimeMessage);
	        }
	        
	        return "Contact Mail Sent Successfully...";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error while Sending Contact Mail";
	    }
	}
	private String quotationRequestToAdmin(RequestQuotationDto requestQuotationDto,List<UserEntity> admins) {
		try {
			// Creating a MimeMessage
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			// Enable multipart mode to handle HTML content
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			
			// Setting up necessary details
			helper.setFrom(sender);
			for(UserEntity admin:admins) {
				
				helper.setTo(admin.getEmail());
				helper.setSubject("Quotation Request");
				
				// HTML content for the email
				String htmlContent = "<html>" +
					    "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>" +
					    "<div style='max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; " +
					    "box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);'>" +
					    "<h2 style='color: #e63946; text-align: center;'>New Quotation Request</h2>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>Hello "+admin.getName()+",</p>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>A new quotation request has been received. Below are the details submitted by the customer:</p>" +
					    "<div style='margin: 20px 0; padding: 10px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
					        "<p><strong>Paper Type:</strong> " + requestQuotationDto.getPaperType() + "</p>" +
					        "<p><strong>Layers:</strong> " + requestQuotationDto.getLayers() + "</p>" +
					        "<p><strong>Quantity:</strong> " + requestQuotationDto.getQuantity() + "</p>" +
					        "<p><strong>Dimensions:</strong> " + requestQuotationDto.getDimensions() + "</p>" +
					        "<p><strong>Message:</strong> " + requestQuotationDto.getMessage() + "</p>" +
					    "</div>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>Please review this request and take the necessary actions to proceed.</p>" +
					    "<p style='margin-top: 20px; color: #555; font-size: 12px;'>Best Regards,<br>Automated Notification System</p>" +
					    "</div>" +
					    "</body>" +
					    "</html>";
				
				// Set the HTML content
				helper.setText(htmlContent, true);
				
				// Send the email
				javaMailSender.send(mimeMessage);
			}
			
			return "Quotation Request Mail Sent Successfully...";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error while Sending Quotation Request Mail";
		}
	}
	private String quotationRequestToCustomer(EmailDetails details,RequestQuotationDto quotationDto) {
		try {
	        // Creating a MimeMessage
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

	        // Enable multipart mode to handle HTML content
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

	        // Setting up necessary details
	        helper.setFrom(sender);
	        helper.setTo(details.getRecipient());
	        helper.setSubject(details.getSubject());

	        // HTML content for the email
	        String htmlContent = "<html>" +
	        	    "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>" +
	        	    "<div style='max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; " +
	        	    "box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);'>" +
	        	    "<h2 style='color: #0056b3; text-align: center;'>Thank You for Your Request!</h2>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>Dear Customer,</p>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>Thank you for reaching out to us. We have received your quotation request, and our executives will get back to you shortly with further details. Below is the summary of the information you provided:</p>" +
	        	    "<div style='margin: 20px 0; padding: 10px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
	        	        "<p><strong>Paper Type:</strong> " + quotationDto.getPaperType() + "</p>" +
	        	        "<p><strong>Layers:</strong> " + quotationDto.getLayers() + "</p>" +
	        	        "<p><strong>Quantity:</strong> " + quotationDto.getQuantity() + "</p>" +
	        	        "<p><strong>Dimensions:</strong> " + quotationDto.getDimensions() + "</p>" +
	        	        "<p><strong>Message:</strong> " + quotationDto.getMessage() + "</p>" +
	        	    "</div>" +
	        	    "<p style='margin: 10px 0; line-height: 1.5;'>We appreciate your interest in our services and are committed to providing you with the best solutions. If you have any additional queries or updates, feel free to reach out to us.</p>" +
	        	    "<p style='margin-top: 20px; color: #555; font-size: 12px;'>Best Regards,<br>The Customer Support Team</p>" +
	        	    "</div>" +
	        	    "</body>" +
	        	    "</html>";

	        // Set the HTML content
	        helper.setText(htmlContent, true);

	        // Send the email
	        javaMailSender.send(mimeMessage);

	        return "Quotation Mail Sent Successfully...";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error while Sending Quotation Mail";
	    }
	}

	@Override
	public String requestOrderCancellation(Long userId, RequestOrderCanellationDto requestOrderCanellationDto) {
		UserEntity userFound = userDao.findById(userId)
				.orElseThrow(()-> new NoSuchResourceFound("Error"));
		OrderEntity orderFound = orderDao.findById(requestOrderCanellationDto
				.getOrderId()).orElseThrow(()-> new NoSuchResourceFound("Error"));
		List<UserEntity> adminsFound = userDao.findByRole(Role.ROLE_ADMIN);
		try {
			// Creating a MimeMessage
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			// Enable multipart mode to handle HTML content
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			
			// Setting up necessary details
			helper.setFrom(sender);
			for(UserEntity admin:adminsFound) {
				
				helper.setTo(admin.getEmail());
				helper.setSubject("Cancellation Request");
				
				// HTML content for the email
				String htmlContent = "<html>" +
					    "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>" +
					    "<div style='max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; " +
					    "box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);'>" +
					    "<h2 style='color: #e63946; text-align: center;'>Order Cancellation Request</h2>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>Hello Admin,</p>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>The customer "+ userFound.getName()+" has requested to cancel their order. Below are the details provided:</p>" +
					    "<div style='margin: 20px 0; padding: 10px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
					        "<p><strong>Order Id:</strong> " + orderFound.getId() + "</p>" +
					        "<p><strong>Paper Type:</strong> " + orderFound.getPaperType() + "</p>" +
					        "<p><strong>Layers:</strong> " + orderFound.getLayers() + "</p>" +
					        "<p><strong>Quantity:</strong> " + orderFound.getQuantity() + "</p>" +
					        "<p><strong>Dimensions:</strong> " + orderFound.getDimensions() + "</p>" +
					        "<p><strong>Order Status:</strong> " + orderFound.getOrderStatus() + "</p>" +
					        "<p><strong>Customer's Message:</strong> " + requestOrderCanellationDto.getMessage() + "</p>" +
					    "</div>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>Please process this cancellation request at the earliest convenience and update the system accordingly.</p>" +
					    "<p style='margin: 10px 0; line-height: 1.5;'>If you need further clarification, feel free to contact the customer directly.</p>" +
					    "<p style='margin-top: 20px; color: #555; font-size: 12px;'>Best Regards,<br>Automated Notification System</p>" +
					    "</div>" +
					    "</body>" +
					    "</html>";
				
				// Set the HTML content
				helper.setText(htmlContent, true);
				
				// Send the email
				javaMailSender.send(mimeMessage);
			}
			
			helper.setFrom(sender);
	        helper.setTo(userFound.getEmail());
	        helper.setSubject("Cancellation Request");
	        
			String htmlContent = "<html>" +
				    "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>" +
				    "<div style='max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; " +
				    "box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);'>" +
				    "<h2 style='color: #e63946; text-align: center;'>Order Cancellation Confirmation</h2>" +
				    "<p style='margin: 10px 0; line-height: 1.5;'>Dear Customer,</p>" +
				    "<p style='margin: 10px 0; line-height: 1.5;'>We have received your request to cancel your order. Below are the details of your order for your reference:</p>" +
				    "<div style='margin: 20px 0; padding: 10px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
				        "<p><strong>Paper Type:</strong> " + orderFound.getPaperType() + "</p>" +
				        "<p><strong>Layers:</strong> " + orderFound.getLayers() + "</p>" +
				        "<p><strong>Quantity:</strong> " + orderFound.getQuantity() + "</p>" +
				        "<p><strong>Dimensions:</strong> " + orderFound.getDimensions() + "</p>" +
				        "<p><strong>Message:</strong> " + requestOrderCanellationDto.getMessage() + "</p>" +
				        "<p><strong>Order Status:</strong> Cancellation Requested</p>" +
				    "</div>" +
				    "<p style='margin: 10px 0; line-height: 1.5;'>We are currently processing your cancellation request and will update you as soon as it is finalized. If you have any additional instructions or need further assistance, please do not hesitate to reach out to us.</p>" +
				    "<p style='margin: 10px 0; line-height: 1.5;'>Thank you for giving us the opportunity to assist you. We hope to serve you again in the future.</p>" +
				    "<p style='margin-top: 20px; color: #555; font-size: 12px;'>Best Regards,<br>The Customer Support Team</p>" +
				    "</div>" +
				    "</body>" +
				    "</html>";
			
			// Set the HTML content
			helper.setText(htmlContent, true);
			
			// Send the email
			javaMailSender.send(mimeMessage);
			
	        return "success";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "error";
	    }
	}
}
