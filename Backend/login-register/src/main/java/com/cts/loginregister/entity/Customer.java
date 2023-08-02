package com.cts.loginregister.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long customerId;
	@NotEmpty(message="Name should not be null")
	private String customerName;
	@NotEmpty
	@Email(message="invalid email address")
	private String customerEmail;
	@NotEmpty(message="Password cannot be null")
	private String customerPassword;
	

}
