package com.dimar.map2saveme.repository;

import androidx.annotation.NonNull;
import com.dimar.map2saveme.models.Animal;
import com.dimar.map2saveme.models.Photo;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class PhotoRepository {

    private static DatabaseReference photoDatabase;

    public PhotoRepository (){
        if(photoDatabase == null){
            photoDatabase = FirebaseDatabase.getInstance().getReference("photos");
        }
    }

    //CeateReadUpdateDelete

    public void save(Photo photo){
        String id=photoDatabase.push().getKey();
        photoDatabase.child(id).setValue(photo);
    }

//    public void readListener(){
//
//    }

    public List<Photo> readList(){
        List<Photo> list=new ArrayList<>();
        photoDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot animalData : dataSnapshot.getChildren()){
                    Photo photo=animalData.getValue(Photo.class);
                    list.add(photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public static DatabaseReference getPhotoDatabase() {
        return photoDatabase;
    }
}
