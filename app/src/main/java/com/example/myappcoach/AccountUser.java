package com.example.myappcoach;

public class AccountUser {
    private String username, phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountUser(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }
}
