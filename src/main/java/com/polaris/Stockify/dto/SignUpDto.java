package com.polaris.Stockify.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpDto {
    
	@NotNull(message="Email is required")
    @Size(min=2,max=50 ,message="Username must be between 2-50 characters")
	private String username;
	
    @NotNull(message="Email is required")
    @Email(message="Email address not valid")
    private String email;
    
    @NotNull(message="Password is required")
    @Size(min=2,max=50 ,message="Password must be between 2-50 characters")
    private String password;
    
    

   	public SignUpDto() {
		super();
	}

	

	public SignUpDto(
			@NotNull(message = "Email is required") @Size(min = 2, max = 50, message = "Username must be between 2-50 characters") String username,
			@NotNull(message = "Email is required") @Email(message = "Email address not valid") String email,
			@NotNull(message = "Password is required") @Size(min = 2, max = 50, message = "Password must be between 2-50 characters") String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

   	   	
}
