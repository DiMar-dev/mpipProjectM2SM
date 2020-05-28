package com.dimar.map2saveme.repository;

import androidx.annotation.NonNull;
import com.dimar.map2saveme.models.Animal;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    private static DatabaseReference animalDatabase;

    public AnimalRepository (){
        if(animalDatabase == null){
            animalDatabase = FirebaseDatabase.getInstance().getReference("animals");
        }
    }

    //CeateReadUpdateDelete

    public void save(Animal animal){
        animalDatabase.child(animal.getNameId()).setValue(animal);
    }

//    public void readListener(){
//
//    }

    public List<Animal> readList(){
        List<Animal> list=new ArrayList<>();
        animalDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot animalData : dataSnapshot.getChildren()){
                    Animal animal=animalData.getValue(Animal.class);
                    list.add(animal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public static DatabaseReference getAnimalDatabase() {
        return animalDatabase;
    }
}
