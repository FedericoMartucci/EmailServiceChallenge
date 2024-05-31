package com.email.emailService.providers;


import java.sql.*;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.email.emailService.Jwt.JwtAuthenticationFilter;
import com.email.emailService.Jwt.JwtService;
import com.email.emailService.model.Email;
import com.email.emailService.repository.EmailRepository;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailProviderServiceImpl implements EmailProviderService{

	private final EmailRepository emailRepository;
	private final JwtAuthenticationFilter jwt;
	private static final String url = "jdbc:mysql://localhost:3306/securitydb";
	private static final String user = "root";
	private static final String key = "";
	public MessageResponse sendSimpleMessage(EmailRequest request) {
	    MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(System.getenv("MAILGUN_API_KEY"))
	        .createApi(MailgunMessagesApi.class);

	    Message message = Message.builder()
	        .from(request.getFromEmail())
	        .to(request.getToEmail())
	        .subject(request.getSubject())
	        .text(request.getText())
	        .build();

		saveEmail(request);
	   	    
	    return mailgunMessagesApi.sendMessage(System.getenv("MAILGUN_DOMAIN_NAME"), message);
	}
	
	public SendEmailsResponse sendSimpleMessageMailjet(EmailRequest request) throws MailjetException{
		ClientOptions options = ClientOptions.builder()
                .apiKey(System.getenv("MAILJET_API_KEY"))
                .apiSecretKey(System.getenv("MAILJET_API_SECRET_KEY"))
                .build();
        MailjetClient client = new MailjetClient(options);

		TransactionalEmail message = TransactionalEmail
                .builder()
                .to(new SendContact(request.getToEmail(), request.getToEmail()))
                .from(new SendContact(request.getFromEmail(), request.getFromEmail()))
                .subject(request.getSubject())
                .textPart(request.getText())
                .build();

        SendEmailsRequest sendEmailsRequest = SendEmailsRequest
                .builder()
                .message(message)
                .build();
        saveEmail(request);
        return sendEmailsRequest.sendWith(client);
	}
	
	public Integer emailsSent(String username) {
		LocalDateTime datetime = LocalDateTime.now();
		Integer emailsSent = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, user, key);
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) 'Emails Sent' FROM email WHERE from_email = '" + username + "' AND SUBSTRING(date_of_email, 1, "+ String.valueOf(datetime.getDayOfMonth()).length() +") = '"+ String.valueOf(datetime.getDayOfMonth()) +"';");
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			emailsSent = resultSet.getInt(1);
			connection.close();
			preparedStatement.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return emailsSent;
	}
	
	public String getUsername(HttpServletRequest request, JwtService jwtService) {
		String token = jwt.getTokenFromRequest(request);
		return jwtService.getUsernameFromToken(token);
	}

	public void saveEmail(EmailRequest request) {
		Email email = Email.builder()
				.fromEmail(request.getFromEmail())
				.toEmail(request.getToEmail())
				.subject(request.getSubject())
				.text(request.getText())
				.dateOfEmail(request.getDateOfEmail())
				.build();
		emailRepository.save(email);
	}
}