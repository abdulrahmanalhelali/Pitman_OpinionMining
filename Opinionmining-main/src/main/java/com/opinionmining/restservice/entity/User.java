package com.opinionmining.restservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;

@NoArgsConstructor
@Document
public class User {

    @Id
    @Getter
    @Setter
    private @NonNull ObjectId id;
    public ObjectId getId() {
		return this.id;
	}

    @Getter
    @Setter
    @Indexed(unique = true)
    private @NonNull String username;
    public String getUsername() {
		return this.username;
	}

    @Getter
    @Setter
    private @NonNull String password;
    public String getPassword() {
		return this.password;
	}

    @Getter
    @Setter
    @Indexed(unique = true)
    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private @NonNull String email;

    @Getter
    @Setter
    private String fbToken;
    public void setFbToken(String fbToken2) {
		this.fbToken = fbToken2;
	}
	public String getFbToken() {
		return this.fbToken;
	}
    @Getter
    @Setter
    private String twToken;
	public void setTwToken(String twToken2) {
		this.twToken = twToken2;
	}
	public String getTwToken() {
		return this.twToken;
	}

    public User(@NonNull String username, @NonNull String email, @NonNull String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    

	

	

	

	
}
