package com.email.emailService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email")
public class Email {
	@Id
	@GeneratedValue
	private Integer id;
	private String fromEmail;
	private String toEmail;
	private String subject;
	private String text;
	private String dateOfEmail;
}
