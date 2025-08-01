package com.example.smartmedicalinventory.model;

public class User {
    private String userId;
    private String password;
    private String userType;

    public User(String userId, String password, String userType) {
        this.userId = userId;
        this.password = password;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}
