package com.example.mystore;

import java.io.Serializable;

public class Account implements Serializable {
    private String key;
    private String username;
    private String password;

    public Account() {

    }

    public Account(String key, String username, String password) {
        this.key = key;
        this.username = username;
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
