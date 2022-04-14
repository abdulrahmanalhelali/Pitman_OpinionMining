package com.opinionmining.restservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Token {
    @Getter
    @Setter
    private String token;

	public void setToken(String login) {
		this.token = login;
		
	}

	public String getToken() {
		return this.token;
	}
}
