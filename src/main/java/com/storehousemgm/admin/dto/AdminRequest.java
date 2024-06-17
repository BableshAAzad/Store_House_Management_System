package com.storehousemgm.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AdminRequest {
	
	@NotNull(message = "UserName can not be null")
	@NotBlank(message = "UserName can not be blank")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters.")
	private String name;
	
//	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	@NotBlank(message = "Email can not be blank")
	@NotNull(message = "Email can not be null")
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "email should be contain suffix @gmail.com")
	private String email;
	
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password must"
			+ " contain at least one letter, one number, one special character")
	@NotBlank(message = "Password can not be blank")
	private String password;
	
}
