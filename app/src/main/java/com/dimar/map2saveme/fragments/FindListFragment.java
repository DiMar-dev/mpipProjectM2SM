package com.dimar.map2saveme.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.*;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dimar.map2saveme.DetailsActivity;
import com.dimar.map2saveme.MainActivity;
import com.dimar.map2saveme.R;
import com.dimar.map2saveme.adapters.CustomListAdapter;
import com.dimar.map2saveme.clickListener.RecyclerViewClickListener;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.viewModel.FindListViewModel;

import java.util.List;


public class FindListFragment extends Fragment implements RecyclerViewClickListener {

    boolean dualPane;
    int curCheckPosition=0;

    CustomListAdapter adapter;
    FindListViewModel findListViewModel;

    Toolbar mytoolbar;

    public FindListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_find_list, container, false);

        mytoolbar= rootView.findViewById(R.id.toolbar2);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mytoolbar);

        initListView(rootView);
        initData();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View detailsFrame = getActivity().findViewById(R.id.details);
        dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            curCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (dualPane) {
            mytoolbar= getActivity().findViewById(R.id.toolbarLandscape);
            ((AppCompatActivity)getActivity()).setSupportActionBar(mytoolbar);
            onCreateOptionsMenu(mytoolbar.getMenu(),getActivity().getMenuInflater());
            showDetails(curCheckPosition);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", curCheckPosition);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.appbar_menu,menu);
        if(dualPane){
            menu.findItem(R.id.menu_item2).setVisible(true);
        }else{
            menu.findItem(R.id.menu_item2).setVisible(false);
        }
        menu.findItem(R.id.menu_item1).setVisible(false);
        menu.findItem(R.id.menu_item3).setVisible(true);
        menu.findItem(R.id.menu_item4).setVisible(false);

        SearchView searchView= (SearchView) menu.findItem(R.id.menu_item3).getActionView();
        searchView.setOnQueryTextListener(getOnQueryTextListener());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item2) {///
            startActivity(new Intent(getContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private SearchView.OnQueryTextListener getOnQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        };
    }


    void showDetails(int index) {
        curCheckPosition = index;

        if (dualPane) {

            // Check what fragment is currently shown, replace if needed.
            DetailsFragment details = (DetailsFragment)
                    getActivity().getSupportFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                // Make new fragment to show this selection.
                details = DetailsFragment.newInstance(index);


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {

            Intent intent = new Intent();
            intent.setClass(requireActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            intent.putExtra("object",adapter.getDataset().get(index).toString());
            startActivity(intent);
        }
    }

    public void initData() {
        findListViewModel=new ViewModelProvider(requireActivity()).get(FindListViewModel.class);
        LiveData<List<Photo>> photoLiveData=findListViewModel.getDataSnapshotLiveData();

        photoLiveData.observe(getViewLifecycleOwner(),data -> {
            if(data!=null){
                adapter.updateDataset(data.get(0));
            }
        });
    }

    private void initListView(View root) {
        RecyclerView recyclerView =  root.findViewById(R.id.recylerView_photos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CustomListAdapter(getContext(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void recyclerViewListClicked(View view, int position) {

        findListViewModel.select(adapter.getDataset().get(position));
        showDetails(position);

    }
}
