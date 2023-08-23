package com.email.emailService.providers;


import org.springframework.stereotype.Service;

import com.email.emailService.model.Email;
import com.email.emailService.repository.EmailRepository;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailProviderService{
	
	private static final String API_KEY = "58c7a7b7ce1e80ddbb2ecb8d6fcb38f3-f0e50a42-10d32ac2";
	private static final String YOUR_DOMAIN_NAME = "sandbox9309a336a5974fe4b847fabf92b91d84.mailgun.org";
	private final EmailRepository emailRepository;
	
	public MessageResponse sendSimpleMessage(EmailRequest request) {
	    MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(API_KEY)
	        .createApi(MailgunMessagesApi.class);

	    Message message = Message.builder()
	        .from(request.getFromEmail())
	        .to(request.getToEmail())
	        .subject(request.getSubject())
	        .text(request.getText())
	        .build();
	    Email email = Email.builder()
	    	.fromEmail(request.getFromEmail())
	    	.toEmail(request.getToEmail())
	        .subject(request.getSubject())
	        .text(request.getText())
	        .dateOfEmail(request.getDateOfEmail())
	    	.build();
	    emailRepository.save(email);
	    
	    return mailgunMessagesApi.sendMessage(YOUR_DOMAIN_NAME, message);
	}
}