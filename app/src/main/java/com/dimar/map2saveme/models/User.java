package com.dimar.map2saveme.models;

import java.util.HashMap;
import java.util.List;

public class User {

    private String userId;

    private String name;

    private String surname;

    private String phone;

    private String email;

    private boolean adoptHelper;

//    private HashMap<String,Boolean> photosIds;


    public User(String userId, String name, String surname, String phone, String email, boolean adoptHelper) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.adoptHelper = adoptHelper;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdoptHelper() {
        return adoptHelper;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdoptHelper(boolean adoptHelper) {
        this.adoptHelper = adoptHelper;
    }
}
