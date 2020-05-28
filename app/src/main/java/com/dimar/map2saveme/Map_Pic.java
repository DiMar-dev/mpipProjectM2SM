package com.dimar.map2saveme;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dimar.map2saveme.models.Animal;
import com.dimar.map2saveme.models.GuestPom;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.repository.AnimalRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class Map_Pic extends AppCompatActivity {

    EditText image;
    EditText lng;
    EditText ltd;
    EditText photographer;
    EditText animal;
    Button submitBt;

    private DatabaseReference mapDatabase;
    private AnimalRepository animDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__pic);

        mapDatabase= FirebaseDatabase.getInstance().getReference("photos");
        animDatabase= new AnimalRepository();

        image=findViewById(R.id.imageBase64);
        lng=findViewById(R.id.mapLng);
        ltd=findViewById(R.id.mapLtd);
        photographer=findViewById(R.id.userID);
        animal=findViewById(R.id.animName);
        submitBt=findViewById(R.id.mapBt);


        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBase();
            }
        });
    }


    private void addToBase() {

        String baseName=photographer.getText().toString().trim();
        String baseAnimal=animal.getText().toString().trim();
        String baseId=image.getText().toString().trim();
        Double maplng= Double.valueOf(this.lng.getText().toString());
        Double mapltd= Double.valueOf(this.ltd.getText().toString().trim());

        String id=mapDatabase.push().getKey();

        Photo photo=new Photo(id,baseId,maplng,mapltd,baseName,baseAnimal);
        Animal animal=new Animal(baseAnimal,baseAnimal.contains("dog") ? true : false);

        mapDatabase.child(id).setValue(photo);
        animDatabase.save(animal);

        Intent intent=new Intent(this,FindList.class);
        startActivity(intent);

    }
}
