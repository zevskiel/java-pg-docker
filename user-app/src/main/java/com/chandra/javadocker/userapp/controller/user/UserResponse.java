package com.chandra.javadocker.userapp.controller.user;

public class UserResponse {
    private String name;

    public UserResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UserResponse setName(String name) {
        this.name = name;
        return this;
    }
}
