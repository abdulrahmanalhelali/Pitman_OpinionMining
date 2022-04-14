package com.opinionmining.restservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserDto {

    @Getter
    @Setter
    private String username;
    public String getUsername() {
		return this.username;
	}

    @Getter
    @Setter
    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String email;
    public String getEmail() {
		return this.email;
	}


    @Getter
    @Setter
    private String password;
    public String getPassword() {
		return this.password;
	}

	

	

	
}
