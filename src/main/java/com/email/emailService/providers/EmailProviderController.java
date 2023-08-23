package com.email.emailService.providers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailProviderController {

	private final EmailProviderService emailProviderService;

	@PostMapping("/send")
	public String sendEmail(@RequestBody EmailRequest request) {
		emailProviderService.sendSimpleMessage(request);
		return "The email has been sent successfully.";
	}
	
}
