package com.chandra.javadocker.userapp.controller.user;

public class UserUpdateNameRequest {
    private String name;

    public String getName() {
        return name;
    }

    public UserUpdateNameRequest setName(String name) {
        this.name = name;
        return this;
    }
}
