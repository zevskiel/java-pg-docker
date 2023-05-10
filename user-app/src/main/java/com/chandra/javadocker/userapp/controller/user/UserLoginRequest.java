package com.chandra.javadocker.userapp.controller.user;

public class UserLoginRequest {
    private String phoneNumber;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserLoginRequest setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
