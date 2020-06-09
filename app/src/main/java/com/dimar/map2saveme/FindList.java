package com.dimar.map2saveme;

import android.os.AsyncTask;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dimar.map2saveme.adapters.CustomListAdapter;
import com.dimar.map2saveme.fragments.FindListFragment;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.viewModel.FindListViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Locale;

public class FindList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_list);

    }



}
