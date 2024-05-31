package com.email.emailService.providers;

import com.email.emailService.Jwt.JwtAuthenticationFilter;
import com.email.emailService.Jwt.JwtService;
import com.email.emailService.model.Email;
import com.email.emailService.repository.EmailRepository;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailProviderServiceImplTest {
    private EmailProviderServiceImpl emailProviderServiceImpl;
    @Mock
    private EmailRepository emailRepository;
    @Mock
    JwtAuthenticationFilter jwt;

    private static final String url = "jdbc:mysql://localhost:3306/securitydb";
    private static final String user = "root";
    private static final String key = "";
    private Connection connection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailProviderServiceImpl = new EmailProviderServiceImpl(emailRepository, jwt);
    }

    @Test
    public void sendSimpleMessageTest() {
        MailgunMessagesApi mailgunMessagesApi = mock(MailgunMessagesApi.class);
        when(mailgunMessagesApi.sendMessage(anyString(), any(Message.class)))
                .thenReturn(new MessageResponse("5555", "Message sent successfully"));

        EmailRequest request = new EmailRequest("from@example.com", "to@example.com", "Subject", "Message");

        MessageResponse response = emailProviderServiceImpl.sendSimpleMessage(request);

        assertEquals(new MessageResponse("5555", "Message sent successfully"), request);
    }

    @Test
    void getUsernameTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        JwtService jwtService = mock(JwtService.class);
        when(jwtService.getUsernameFromToken(Mockito.isNull())).thenReturn("testingUser");

        String username = emailProviderServiceImpl.getUsername(request, jwtService);

        assertEquals("testingUser", username);
    }

    @Test
    public void saveEmailTest() {

        EmailRequest request = new EmailRequest("from@example.com", "to@example.com", "Subject", "Message");


        Email expectedEmail = Email.builder()
                .fromEmail("from@example.com")
                .toEmail("to@example.com")
                .subject("Subject")
                .text("Message")
                .dateOfEmail("5-SEPTEMBER-2023")
                .build();

        when(emailRepository.save(any(Email.class))).thenReturn(expectedEmail);

        emailProviderServiceImpl.saveEmail(request);

        verify(emailRepository).save(expectedEmail);
    }

    @Test
    public void emailsSentTest() throws SQLException {
        connection = DriverManager.getConnection(url, user, key);
        insertTestData();
        connection.close();

        Integer emailsSent = emailProviderServiceImpl.emailsSent("tester");

        assertEquals(1, emailsSent);
    }

//    private void createTable() throws SQLException {
//        String createTableSQL = "CREATE TABLE email (from_email VARCHAR(255), date_of_email VARCHAR(10))";
//        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
//        preparedStatement.execute();
//    }

    private void insertTestData() throws SQLException {
        String insertSQL = "INSERT INTO email (id, from_email, date_of_email) VALUES (8000, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

        preparedStatement.setString(1, "tester");
        preparedStatement.setString(2, "5-SEPTEMBER-2023");
        preparedStatement.execute();

    }
}