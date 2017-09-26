package com.example.dhirajnayak.zeriondhirajnayak.model;

/**
 * Created by dhirajnayak on 9/24/17.
 */

public class LoginResponse {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String code, message, token;
}
