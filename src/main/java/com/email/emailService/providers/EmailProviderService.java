package com.email.emailService.providers;

import com.email.emailService.Jwt.JwtService;
import com.email.emailService.repository.EmailRepository;
import com.mailgun.model.message.MessageResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.response.SendEmailsResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface EmailProviderService {
	MessageResponse sendSimpleMessage(EmailRequest request);
	SendEmailsResponse sendSimpleMessageMailjet(EmailRequest request) throws MailjetException;
	Integer emailsSent(String username);
	String getUsername(HttpServletRequest request, JwtService jwtService);
	void saveEmail(EmailRequest request);
}
