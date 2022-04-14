package com.opinionmining.restservice.model;

public class JwtUser {

    private String username;
    private String id;

    public JwtUser(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;

    }
    public String getUsername(){
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
