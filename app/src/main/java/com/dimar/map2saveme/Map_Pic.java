package com.dimar.map2saveme;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dimar.map2saveme.models.Animal;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.repository.Repository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Map_Pic extends AppCompatActivity {

    EditText getImage;
    EditText getTxtLng;
    EditText getTxtLtg;
    EditText getUser;
    EditText getAnim;
    Button sub;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__pic);

        repository=new Repository();

        getImage=findViewById(R.id.imageBase64);
        getTxtLng=findViewById(R.id.mapLng);
        getTxtLtg=findViewById(R.id.mapLtd);
        getUser=findViewById(R.id.userID);
        getAnim=findViewById(R.id.animName);
        sub=findViewById(R.id.mapBt);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

    }

    private void loadData() {

        String imageBase=getImage.getText().toString().trim();
        Double lng= Double.valueOf(getTxtLng.getText().toString().trim());
        Double ltd= Double.valueOf(getTxtLtg.getText().toString().trim());
        String userPhoto=getUser.getText().toString().trim();
        String animID=getAnim.getText().toString().trim();

        Photo photo=new Photo(Repository.getPhotoKey(),imageBase,lng,ltd,userPhoto,animID);
        repository.save(photo);


        Animal animal=new Animal(animID,animID.toLowerCase().contains("dog") ? true:false);
        repository.save(animal);

    }

}
