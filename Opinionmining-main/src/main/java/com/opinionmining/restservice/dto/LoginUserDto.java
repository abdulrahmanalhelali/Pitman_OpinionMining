package com.opinionmining.restservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

	public String getUsername() {
		
		return this.username;
	}

	public String getPassword() {
		
		return this.password;
	}
}
