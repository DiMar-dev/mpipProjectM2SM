package com.dimar.map2saveme.models;

import com.dimar.map2saveme.models.enums.AnimalType;

import java.util.HashMap;

public class Animal {

    private String nameId;

    private boolean isDog;

    private boolean isCat;
//    private HashMap<String, Boolean> photos;


    public Animal(String nameId, boolean isDog) {
        this.nameId = nameId;
        this.isDog = isDog;
        this.isCat=!isDog;
    }

    public Animal() {
    }

    public String getNameId() {
        return nameId;
    }

    public boolean isDog() {
        return isDog;
    }

    public boolean isCat() {
        return isCat;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public void setDog(boolean dog) {
        isDog = dog;
    }

    public void setCat(boolean cat) {
        isCat = cat;
    }
}
