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
}
