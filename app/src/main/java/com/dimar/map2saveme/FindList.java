package com.dimar.map2saveme;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dimar.map2saveme.models.Animal;
import com.dimar.map2saveme.repository.AnimalRepository;

import java.util.List;

public class FindList extends AppCompatActivity {

    AnimalRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_list);

    }
}
