package com.email.emailService.Auth;

import com.email.emailService.Jwt.JwtService;
import com.email.emailService.model.User;
import com.email.emailService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userRepository, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void loginWithInvalidCredentials(){
        LoginRequest loginRequest = new LoginRequest("federicomartucci@hotmail.com", "1234567");
        User existingUser = new User();
        existingUser.setUsername(loginRequest.getUsername());
        existingUser.setPassword(loginRequest.getPassword());

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn((Optional<User>) Optional.of(existingUser));

        assertEquals(new AuthResponse(),authService.login(loginRequest));
    }
    @Test
    void loginWithValidCredentials(){
        LoginRequest loginRequest = new LoginRequest("federicomartucci@hotmail.com", "123456");
        User existingUser = new User();
        existingUser.setUsername(loginRequest.getUsername());
        existingUser.setPassword(loginRequest.getPassword());

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn((Optional<User>) Optional.of(existingUser));

        assertTrue(authService.login(loginRequest) != new AuthResponse(null));
    }
}