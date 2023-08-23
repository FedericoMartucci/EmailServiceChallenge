package com.email.emailService.providers;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

	private String fromEmail;
	private String toEmail;
	private String subject;
	private String text;
	private String dateOfEmail;
	
	public String getDateOfEmail() {
		LocalDateTime datetime = LocalDateTime.now();
		return String.valueOf(datetime.getDayOfMonth())+"-"+
				String.valueOf(datetime.getMonth())+"-"+
				String.valueOf(datetime.getYear());
	}
}
