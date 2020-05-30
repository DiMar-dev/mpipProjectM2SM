package com.dimar.map2saveme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dimar.map2saveme.adapters.CustomListAdapter;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.viewModel.FindListViewModel;

import java.util.List;
import java.util.Locale;

public class FindList extends AppCompatActivity {

    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_list);

        initToolbar();
        initListView();
        initData();

    }

    public void initData() {
        FindListViewModel findListViewModel=new ViewModelProvider(this).get(FindListViewModel.class);
        LiveData<List<Photo>> photoLiveData=findListViewModel.getDataSnapshotLiveData();

        photoLiveData.observe(this,data -> {
            if(data!=null){
                adapter.updateDataset(data);
            }
        });
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarFind);
       // setSupportActionBar(toolbar);
    }

    private void initListView() {
        RecyclerView recyclerView =  findViewById(R.id.recylerView_photos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomListAdapter();
        recyclerView.setAdapter(adapter);
    }


}
