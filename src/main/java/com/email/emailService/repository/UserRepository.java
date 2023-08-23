package com.email.emailService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.emailService.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
