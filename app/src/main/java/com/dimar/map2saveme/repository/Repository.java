package com.dimar.map2saveme.repository;

import androidx.annotation.NonNull;
import com.dimar.map2saveme.firebaseAuth.FirebaseCallback;
import com.dimar.map2saveme.models.Animal;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class Repository {

    private static DatabaseReference databaseReference=null;
    private static String photoKey;

    public Repository() {
        if(databaseReference==null){
            //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
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

    public void findUser(FirebaseCallback firebaseCallback,String id){
         databaseReference.child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    firebaseCallback.onCallback(dataSnapshot.getValue(User.class));
                }else{
                    firebaseCallback.onCallback(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
