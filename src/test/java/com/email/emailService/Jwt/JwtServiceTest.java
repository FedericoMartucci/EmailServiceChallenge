package com.email.emailService.Jwt;

import com.email.emailService.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @Test
        void invalidTokenTest(){
        UserDetails userDetails = new User();
        assertFalse(new JwtService().isTokenValid("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZWRlcmljb2FtYXJ0dWNjaUBnbWFpbC5jb20iLCJpYXQiOjE2OTM4NTAzODcsImV4cCI6MTY5Mzg1Mzk4N30.ggGD7lQEv8GaeUsjS7Hmu1wp7nnE4_8Fo9q-AK4aUns", userDetails));
    }

    @Test
    void getUsernameFromTokenTest01(){
        assertEquals("federicomartucci@hotmail.com", new JwtService().getUsernameFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZWRlcmljb21hcnR1Y2NpQGhvdG1haWwuY29tIiwiaWF0IjoxNjkzODUyNjk5LCJleHAiOjE2OTM4NTYyOTl9.Ebu7a5JvDZu4Nl8RKdmShRIQWQvkJj7X2xX2sxXXCos"));
    }

    @Test
    void getUsernameFromNullTokenTest(){
        assertThrows(IllegalArgumentException.class, () -> new JwtService().getUsernameFromToken(""));
    }

    @Test
    void notExpiredTokenTest(){
        assertFalse(new JwtService().isTokenExpired("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZWRlcmljb2FtYXJ0dWNjaUBnbWFpbC5jb20iLCJpYXQiOjE2OTM4NTE5MDQsImV4cCI6MTY5Mzg1NTUwNH0.snCg09rVW61ZQZjrMTKiq71WbpoCyzIfHY0C4ssPu4c"));
    }

}