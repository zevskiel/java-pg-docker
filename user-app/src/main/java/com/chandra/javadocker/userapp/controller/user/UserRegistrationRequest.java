package com.chandra.javadocker.userapp.controller.user;

public class UserRegistrationRequest {
    private String phoneNumber;
    private String name;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegistrationRequest setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRegistrationRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
