package com.email.emailService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.emailService.model.Email;

public interface EmailRepository extends JpaRepository<Email, Integer>{

}
