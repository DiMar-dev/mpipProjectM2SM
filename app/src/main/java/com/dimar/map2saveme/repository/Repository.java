package com.dimar.map2saveme.repository;

import com.dimar.map2saveme.models.Animal;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repository {

    private static DatabaseReference databaseReference=null;
    private static String photoKey;

    public Repository() {
        if(databaseReference==null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
    }

    // Save and Update...za isto ID se prezapisuva
    public void save(Animal animal){
        databaseReference.child("animals").child(animal.getNameId()).setValue(animal);
    }
    public void save(Photo photo){
        databaseReference.child("photos").child(photo.getImageID()).setValue(photo);
    }
    public void save(User user){
        databaseReference.child("users").child(user.getUserId()).setValue(user);
    }

    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public static String getPhotoKey() {
        photoKey=databaseReference.child("photos").push().getKey();
        return photoKey;
    }
}
