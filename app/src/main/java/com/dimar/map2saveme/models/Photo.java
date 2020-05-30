package com.dimar.map2saveme.models;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Photo {

    private String imageID;

    private String imageBase64;

    private LocalDateTime localDateTime;

    private Double lng;

    private Double ltd;

    private String photographerID;

    private String andimalID;

    public Photo(String imageID, String imageBase64, Double lng, Double ltd, String photographerID, String andimalID) {
        this.imageID = imageID;
        this.imageBase64 = imageBase64;
        this.lng = lng;
        this.ltd = ltd;
        this.photographerID = photographerID;
        this.andimalID = andimalID;
        this.localDateTime=LocalDateTime.now();
    }

    public Photo() {
    }

    public String getImageID() {
        return imageID;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public LocalDateTime getDate() {
        return localDateTime;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLtd() {
        return ltd;
    }

    public String getPhotographerID() {
        return photographerID;
    }

    public String getAndimalID() {
        return andimalID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLtd(Double ltd) {
        this.ltd = ltd;
    }

    public void setPhotographerID(String photographerID) {
        this.photographerID = photographerID;
    }

    public void setAndimalID(String andimalID) {
        this.andimalID = andimalID;
    }
}
