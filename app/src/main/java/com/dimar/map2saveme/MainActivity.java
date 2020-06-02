package com.dimar.map2saveme;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dimar.map2saveme.models.GuestPom;
import com.dimar.map2saveme.models.User;
import com.dimar.map2saveme.repository.Repository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText surnme;
    EditText idName;
    Button submitBt;
    Button dataBt;
//    Date date;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository=new Repository();

        name=findViewById(R.id.Name);
        surnme=findViewById(R.id.surname);
        idName=findViewById(R.id.idName);
        submitBt=findViewById(R.id.button);
        dataBt=findViewById(R.id.showDataActivity);
//        date=new Date();
//        idName.setText(date.toString());

        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBase();
            }
        });
        dataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2=new Intent(getApplicationContext(),FindList.class);
                startActivity(intent2);
            }
        });

    }

    private void addToBase() {
        String baseName=name.getText().toString().trim();
        String baseSurname=surnme.getText().toString().trim();
        String baseId=idName.getText().toString().trim();


        if(baseId!="" && baseId!=null){
            User pom = new User(baseId, baseName, baseSurname, "070307348", "pom@email.com", true);
            repository.save(pom);
        }

        Intent intent=new Intent(this,Map_Pic.class);
        startActivity(intent);


    }
}
