package com.dimar.map2saveme.models;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;


public class Photo {

    private String imageID;

    private String imageBase64;

//    private LocalDateTime date;
    private long date;

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
//        this.date=LocalDateTime.now().toEpochSecond((ZoneOffset) ZoneOffset.systemDefault());
        this.date=LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();

    }

    public Photo() {
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLtd() {
        return ltd;
    }

    public void setLtd(Double ltd) {
        this.ltd = ltd;
    }

    public String getPhotographerID() {
        return photographerID;
    }

    public void setPhotographerID(String photographerID) {
        this.photographerID = photographerID;
    }

    public String getAndimalID() {
        return andimalID;
    }

    public void setAndimalID(String andimalID) {
        this.andimalID = andimalID;
    }

    @NonNull
    @Override
    public String toString() {
        return imageID+","+imageBase64+","+photographerID+","+andimalID+","+lng+","+ltd+","+date;
    }
}
