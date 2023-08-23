package com.email.emailService.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.email.emailService.Jwt.JwtService;
import com.email.emailService.model.Role;
import com.email.emailService.model.User;
import com.email.emailService.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
	}

	public AuthResponse register(RegisterRequest request) {
		
		User user = User.builder()
				.username(request.getUsername())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .firstname(request.getFirstName())
	            .lastname(request.getLastName())
	            .country(request.getCountry())
	            .telnumber(request.getTelNumber())
	            .role(Role.USER)
	            .build();

	        userRepository.save(user);

	        return AuthResponse.builder()
	            .token(jwtService.getToken(user))
	            .build();
	}

}
