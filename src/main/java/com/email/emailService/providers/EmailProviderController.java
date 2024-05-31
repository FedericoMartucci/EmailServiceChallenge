package com.email.emailService.providers;

import com.email.emailService.Jwt.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mailjet.client.errors.MailjetException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class
EmailProviderController {

	private final EmailProviderService emailProviderService;
	private final JwtService jwtService;

	@PostMapping("/send")
	public String sendEmail(@RequestBody EmailRequest request, HttpServletRequest httpRequest) {
		request.setFromEmail(emailProviderService.getUsername(httpRequest, jwtService));
		if(emailProviderService.emailsSent(request.getFromEmail()) < 1000) {
			try{
				emailProviderService.sendSimpleMessageMailjet(request); //Email with Mailjet
			} catch(MailjetException e){
				emailProviderService.sendSimpleMessage(request); //Email with Mailgun
			}

			return "The email has been sent successfully.";			
		}
		return "You have reached out your daily limit of 1000 emails per day.\nYour quota will be reseted tomorrow.";
	}
	
}
