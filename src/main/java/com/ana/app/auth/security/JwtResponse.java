package com.ana.app.auth.security;

public class JwtResponse {
    private String token;
    private final String type = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }

    // Standard getters and setters...

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }
}

