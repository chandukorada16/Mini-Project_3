package com.user.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class User {

	private String fullName;

	@NotEmpty(message = "Email is Required")
	@Email(message = "Enter Valid Email id")
	private String email;

	@NotEmpty(message = "Phone Number is Required")
	@Size(min = 10, message = "Phone numebr should atleast 10 digits")
	private Long mobileNo;

	private String gender;

	private String dob;

	private Long ssn;

}
