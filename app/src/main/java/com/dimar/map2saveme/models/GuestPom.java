package com.dimar.map2saveme.models;

import java.util.HashMap;
import java.util.List;

public class GuestPom {

    private String id;

    private String name;

    private String surname;

    private HashMap<String,Boolean> pomIds;

    public GuestPom(String id, String name, String surname, HashMap<String,Boolean> pomIds) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pomIds = pomIds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public HashMap<String,Boolean> getPomIds() {
        return pomIds;
    }
}
